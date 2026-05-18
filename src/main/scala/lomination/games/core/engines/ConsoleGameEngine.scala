package lomination.games.core.engines

import lomination.games.core.gameTypes.{Displayable, ZeroSumGame}
import lomination.games.core.players.{ Player}
import lomination.games.core.shared.{Quit, Move, Outcome, Turn}

object ConsoleGameEngine:

  def run[G <: ZeroSumGame[G, M] & Displayable, M <: Move](
      game: G,
      p1: Player[G, M],
      p2: Player[G, M]
  ): Outcome =
    println(game.display)
    game.winner match
      case Outcome.None =>
        if game.legalMoves.isEmpty then
          throw new IllegalStateException(
            "Outcome is none on current game state but there is no legal move.\n" ++
              "Current game state:\n" ++
              game.display
          )
        else
          val curr = currPlayer(game, p1, p2)
          val move = curr.getMove(game)
          move match
            case Left(m) if !game.isLegal(m) =>
              throw new IllegalArgumentException(
                s"Player ${game.turn} (${currPlayer(game, p1, p2)}) gave move $m which is illegal in current game state.\n" ++
                  "Current game state:\n" ++
                  game.display
              )
            case Left(m) =>
              val newGame = game.move(m).get
              run(newGame, p1, p2)
            case Right(Quit) => Outcome.Win(game.turn.switch)
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
