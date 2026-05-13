package lomination.games.core

import lomination.games.core.gameTypes.{Displayable, ZeroSumGame}
import lomination.games.core.players.Player
import lomination.games.core.shared.{Move, Outcome, Turn}

object ConsoleGameEngine:

  def run[G <: ZeroSumGame[G, M] & Displayable, M <: Move](
      game: G,
      p1: Player[G, M],
      p2: Player[G, M]
  ): Outcome =
    game.display
    game.winner match
      case Outcome.None if game.legalMoves.isEmpty =>
        throw new IllegalStateException(
          "Outcome is none but there is no legal move."
        )
      case Outcome.None =>
        currPlayer(game, p1, p2).getMove(game) match
          case None    => Outcome.Win(game.turn.switch)
          case Some(m) => run(game.move(m).get, p1, p2)
      case outcome =>
        game.display
        outcome

  private def currPlayer[G <: ZeroSumGame[G, M], M <: Move](
      game: G,
      p1: Player[G, M],
      p2: Player[G, M]
  ): Player[G, M] =
    game.turn match
      case Turn.P1 => p1
      case _       => p2
