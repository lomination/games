package lomination.games.core.players

import lomination.games.core.gameTypes.{Evaluatable, Hash, ZeroSumGame}
import lomination.games.core.shared.{Move, Outcome, Score, choose}

case class BoundedMinimax[G <: ZeroSumGame[G, M] & Evaluatable, M <: Move](
    depth: Int
) extends Player[G, M]:

  def getMove(game: G): Option[M] =
    val scores = game.legalMoves
      .foldLeft[(Map[Hash, Score], Map[M, Score])]((Map.empty, Map.empty)) {
        case ((cache, acc), m) =>
          val (newCache, score) = evaluate(game.move(m).get, cache, depth)
          (newCache, acc + (m -> -score))
      }
      ._2
    val maxScore = scores.values.max
    Some(scores.collect { case (m, s) if s == maxScore => m }.toSeq.choose)

  private def evaluate(
      game: G,
      cache: Map[Hash, Score],
      depth: Int
  ): (Map[Hash, Score], Score) =
    val hash = game.eqHash
    if cache.contains(hash) then
      val score = cache(hash)
      (cache, score)
    else
      game.winner match
        case Outcome.Win(t) if t == game.turn =>
          (cache + (hash -> Score.Win), Score.Win)
        case Outcome.Win(_)  => (cache + (hash -> Score.Loose), Score.Loose)
        case Outcome.Draw    => (cache + (hash -> Score.Draw), Score.Draw)
        case _ if depth == 0 =>
          val s = game.score
          (cache + (hash -> s), s)
        case _ =>
          val (finalCache, scores) =
            game.legalMoves.foldLeft((cache, Seq.empty[Score])) {
              case ((c, acc), move) =>
                val (newCache, childScore) =
                  evaluate(game.move(move).get, c, depth - 1)
                (newCache, acc :+ -childScore)
            }
          val bestScore = scores.max
          (finalCache + (hash -> bestScore), bestScore)
