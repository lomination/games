package lomination.games.core.players

import lomination.games.core.gameTypes.ZeroSumGame
import lomination.games.core.shared.{Move, Quit}

import org.jline.keymap.BindingReader
import org.jline.terminal.Terminal

import java.io.PrintWriter

trait JLinePlayer[G <: ZeroSumGame[G, M], M <: Move]:

  def getMove(
      game: G,
      terminal: Terminal,
      reader: BindingReader,
      writer: PrintWriter
  ): Either[M, Quit.type]
