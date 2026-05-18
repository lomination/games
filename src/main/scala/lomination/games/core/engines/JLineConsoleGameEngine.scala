package lomination.games.core.engines

import lomination.games.core.gameTypes.{Displayable, JLineGame, ZeroSumGame}
import lomination.games.core.players.JLinePlayer
import lomination.games.core.shared.{Move, Outcome, Quit, Turn}
import org.jline.keymap.BindingReader
import org.jline.terminal.{Terminal, TerminalBuilder}

import scala.annotation.tailrec

object JLineConsoleGameEngine:

  // Open the terminal in raw mode
  private val terminal: Terminal =
    TerminalBuilder.builder().jna(true).system(true).build()
  terminal.enterRawMode()

  private val reader = new BindingReader(terminal.reader)
  private val writer = terminal.writer()

  @tailrec
  def run[G <: ZeroSumGame[G, M] & JLineGame[M] & Displayable, M <: Move](
      game: G,
      p1: JLinePlayer[G, M],
      p2: JLinePlayer[G, M]
  ): Outcome =
    game.winner match
      case Outcome.None =>
        if game.legalMoves.isEmpty then
          close()
          throw new IllegalStateException("...")
        else
          val curr = currPlayer(game, p1, p2)
          val move = curr.getMove(game, terminal, reader, writer)
          move match
            case Left(m) if !game.isLegal(m) =>
              close()
              throw new IllegalArgumentException("...")
            case Left(m) =>
              val newGame = game.move(m).get
              run(newGame, p1, p2)
            case Right(Quit) =>
              close()
              Outcome.Win(game.turn.switch)
      case outcome =>
        println("\u001b[H\u001b[2J")
        println("Game Over! ")
        println(game.display)
        close()
        outcome

  private def currPlayer[G <: ZeroSumGame[G, M], M <: Move](
      game: G,
      p1: JLinePlayer[G, M],
      p2: JLinePlayer[G, M]
  ): JLinePlayer[G, M] =
    game.turn match
      case Turn.P1 => p1
      case _       => p2

  // Clean up terminal resources when closing the application
  def close(): Unit = terminal.close()
