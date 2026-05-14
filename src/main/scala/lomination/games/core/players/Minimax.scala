package lomination.games.core.players

import lomination.games.core.gameTypes.{Hash, ZeroSumGame}
import lomination.games.core.shared.{Move, Outcome, choose}

// Since this implementation is meant to traverse
// the whole tree, no complex score is needed. Integer
// values 1, 0 and -1 are used to indicate respectively win,
// draw and loose.
private type Score = Int

case class Minimax[G <: ZeroSumGame[G, M], M <: Move]() extends Player[G, M]:

  def getMove(game: G): Option[M] =
    val scores = game.legalMoves
      .foldLeft[(Map[Hash, Score], Map[M, Score])]((Map.empty, Map.empty)) {
        case ((cache, acc), m) =>
          val (newCache, score) = evaluate(game.move(m).get, cache)
          (newCache, acc + (m -> -score))
      }
      ._2
    val maxScore = scores.values.max
    Some(scores.collect { case (m, s) if s == maxScore => m }.toSeq.choose)

  private def evaluate(
      game: G,
      cache: Map[Hash, Score]
  ): (Map[Hash, Score], Score) =
    val hash = game.eqHash

    if cache.contains(hash) then
      val score = cache(hash)
      (cache, score)
    else
      game.winner match
        case Outcome.Win(t) if t == game.turn => (cache + (hash -> 1), 1)
        case Outcome.Win(_)                   => (cache + (hash -> -1), -1)
        case Outcome.Draw                     => (cache + (hash -> 0), 0)
        case Outcome.None                     =>
          val (finalCache, scores) =
            game.legalMoves.foldLeft((cache, Seq.empty[Score])) {
              case ((c, acc), move) =>
                val (newCache, childScore) = evaluate(game.move(move).get, c)
                (newCache, acc :+ -childScore)
            }
          val bestScore = scores.max
          (finalCache + (hash -> bestScore), bestScore)
