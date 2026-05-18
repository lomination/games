package lomination.games.core.players

import lomination.games.core.gameTypes.ZeroSumGame
import lomination.games.core.shared.{Move, Quit}

import scala.annotation.tailrec

trait Human[G <: ZeroSumGame[G, M], M <: Move] extends Player[G, M]:

  def format: String

  def tryParse(input: String): Either[M, String]

  @tailrec
  final def getMove(game: G): Either[M, Quit.type] =
    print(s"Select move ($format): ")
    scala.io.StdIn.readLine() match
      case null => throw new IllegalArgumentException("readLine returned null")
      case "q" | "quit" => Right(Quit)
      case input        =>
        tryParse(input) match
          case Left(move)   => Left(move)
          case Right(error) =>
            println(s"Error: $error")
            getMove(game)
