package lomination.games.core.players

import lomination.games.core.gameTypes.ZeroSumGame
import lomination.games.core.shared.{Move, Quit}

import org.jline.keymap.BindingReader
import org.jline.terminal.Terminal

import java.io.PrintWriter

import scala.annotation.unused

trait Player[G <: ZeroSumGame[G, M], M <: Move] extends JLinePlayer[G, M]:

  /** Note: this function assumes that there is at least one possible move in
    * the current state of the game, i.e. `game.legalMoves` should return a
    * non-empty sequence.
    *
    * @param game
    * @return
    */
  def getMove(game: G): Either[M, Quit.type]

  def getMove(
      game: G,
      @unused terminal: Terminal,
      @unused reader: BindingReader,
      @unused writer: PrintWriter
  ): Either[M, Quit.type] = getMove(game)
