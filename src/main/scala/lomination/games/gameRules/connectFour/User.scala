package lomination.games.gameRules.connectFour

import lomination.games.core.players.Player

import scala.annotation.tailrec

case class User() extends Player[ConnectFour, Move]:

  @tailrec
  final def getMove(game: ConnectFour): Option[Move] =
    print("Select move (col): ")
    scala.io.StdIn.readLine() match
      case null         => None
      case "q" | "quit" => None
      case s            =>
        val m =
          for
            c <- s.trim.toIntOption
            if 0 <= c && c <= 6
          yield Move(c.asInstanceOf[Coord])
        if m.isDefined then m
        else getMove(game)

  private inline def isValidRow(n: Int): Boolean =
    n >= 0 && n <= 5
