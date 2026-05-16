package lomination.games.core.players

import lomination.games.core.gameTypes.{Evaluatable, ZeroSumGame}
import lomination.games.core.shared.{Move, Outcome, Score, choose}

import scala.annotation.tailrec

private inline def max2[T <: Ordered[T]](a: T, b: T): T =
  if a >= b then a else b

case class AlphaBeta[G <: ZeroSumGame[G, M] & Evaluatable, M <: Move](
    depth: Int
) extends Player[G, M]:

  def getMove(game: G): Input[M] =
    val scoredMoves = game.legalMoves.map { m =>
      val score =
        -alphaBeta(game.move(m).get, depth - 1, Score.Loose, Score.Win)
      (m, score)
    }

    val maxScore  = scoredMoves.map(_._2).max
    val bestMoves = scoredMoves.collect { case (m, s) if s == maxScore => m }
    Input.Move(bestMoves.choose)

  /** Classic Negamax Alpha-Beta
    * @param alpha
    *   The minimum score that the maximizing player is assured of.
    * @param beta
    *   The maximum score that the minimizing player is assured of.
    */
  private def alphaBeta(game: G, depth: Int, alpha: Score, beta: Score): Score =
    game.winner match
      case Outcome.Win(t) if t == game.turn => Score.Win
      case Outcome.Win(_)                   => Score.Loose
      case Outcome.Draw                     => Score.Draw
      case _ if depth == 0                  => game.score
      case _                                =>
        loopMoves(game, game.legalMoves.toList, depth, alpha, beta, Score.Loose)

  @tailrec
  private def loopMoves(
      game: G,
      moves: List[M],
      depth: Int,
      alpha: Score,
      beta: Score,
      bestSoFar: Score
  ): Score =
    moves match
      case head :: tail =>
        val nextGame     = game.move(head).get
        val currentScore = -alphaBeta(nextGame, depth - 1, -beta, -alpha)

        val newBest  = max2(currentScore, bestSoFar)
        val newAlpha = max2(newBest, alpha)

        if newAlpha >= beta then newAlpha // Beta cut-off
        else loopMoves(game, tail, depth, newAlpha, beta, newBest)
      case Nil => bestSoFar
