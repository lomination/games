package lomination.games.core.players

import org.jline.terminal.{Terminal}
import org.jline.keymap.BindingReader

import lomination.games.core.shared.{Move, Quit}
import lomination.games.core.gameTypes.ZeroSumGame
import java.io.PrintWriter

trait JLinePlayer[G <: ZeroSumGame[G, M], M <: Move]:

  def getMove(
      game: G,
      terminal: Terminal,
      reader: BindingReader,
      writer: PrintWriter
  ): Either[M, Quit.type]
