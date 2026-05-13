package lomination.games.gameRules.tictactoe

import lomination.games.core.players.Player

import scala.annotation.tailrec

case class User() extends Player[TicTacToe, Move]:

  @tailrec
  final def getMove(game: TicTacToe): Option[Move] =
    print("Select move (row,col): ")
    scala.io.StdIn.readLine() match
      case null         => None
      case "q" | "quit" => None
      case s            =>
        val m = s.split(",") match
          case Array(r, c) =>
            for
              row <- r.trim.toIntOption
              col <- c.trim.toIntOption
              if isValid(row) && isValid(col)
            yield Move(row.asInstanceOf[Coord], col.asInstanceOf[Coord])
          case _ => None
        if m.isDefined then m
        else getMove(game)

  private inline def isValid(n: Int): Boolean =
    n >= 0 && n <= 2
