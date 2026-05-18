package lomination.games.gameRules.tictactoe

import org.jline.terminal.{Terminal}
import org.jline.utils.InfoCmp.Capability
import org.jline.keymap.BindingReader
import org.jline.keymap.KeyMap

import lomination.games.core.players.JLinePlayer
import lomination.games.core.shared.{Move, Quit}
import lomination.games.core.gameTypes.ZeroSumGame
import lomination.games.core.gameTypes.JLineGame
import scala.annotation.tailrec
import java.io.PrintWriter

private enum Event:
  case ArrowUp, ArrowDown, ArrowLeft, ArrowRight, Quit, Select

case class JLineHuman[G <: ZeroSumGame[G, M] & JLineGame[M], M <: Move]()
    extends JLinePlayer[G, M]:

  // Set up JLine key bindings for arrow keys
  private val keys = new KeyMap[Event]()
  keys.bind(Event.ArrowUp, "\u001B[A")
  keys.bind(Event.ArrowDown, "\u001B[B")
  keys.bind(Event.ArrowRight, "\u001B[C")
  keys.bind(Event.ArrowLeft, "\u001B[D")
  keys.bind(Event.Select, "\r", "\n", " ")
  keys.bind(Event.Quit, "q", "Q")
  keys.setNomatch(null)

  def getMove(game: G, terminal: Terminal, reader: BindingReader, writer: PrintWriter): Either[M, Quit.type] =
    // Clear screen once at the start of the turn
    terminal.puts(Capability.clear_screen)
    terminal.flush()

    val (ch, cw) = game.defaultCursorPos
    selectMove(game, ch, cw, terminal, reader, writer)
  
  @tailrec
  private def selectMove(game: G, ch: Int, cw: Int, terminal: Terminal, reader: BindingReader, writer: PrintWriter): Either[M, Quit.type] =
    terminal.puts(Capability.cursor_home)
    
    writer.write(game.displayWithCursor(ch, cw))
    terminal.flush()

    val event = reader.readBinding(keys)
    event match
      case Event.ArrowUp =>
        val (newCh, newCw) = game.onArrowUpPressed(ch, cw)
        selectMove(game, newCh, newCw, terminal, reader, writer)
      case Event.ArrowDown  =>
        val (newCh, newCw) = game.onArrowDownPressed(ch, cw)
        selectMove(game, newCh, newCw, terminal, reader, writer)
      case Event.ArrowRight =>
        val (newCh, newCw) = game.onArrowRightPressed(ch, cw)
        selectMove(game, newCh, newCw, terminal, reader, writer)
      case Event.ArrowLeft =>
        val (newCh, newCw) = game.onArrowLeftPressed(ch, cw)
        selectMove(game, newCh, newCw, terminal, reader, writer)
      case Event.Select    =>
        val attemptedMove = game.coordsToMove(ch, cw)
        if game.isLegal(attemptedMove) then
          Left(attemptedMove)
        else
          writer.println("\u001b[31mIllegal Move!\u001b[0m")
          terminal.flush()
          selectMove(game, ch, cw, terminal, reader, writer)
      case Event.Quit => Right(Quit)
      case null =>
        selectMove(game, ch, cw, terminal, reader, writer)
