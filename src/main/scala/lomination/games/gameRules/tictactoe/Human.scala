package lomination.games.gameRules.tictactoe

case class Human() extends lomination.games.core.players.Human[TicTacToe, Move]:

  def format: String = "row,col"

  def tryParse(input: String): Either[Move, String] =
    input.split(",") match
      case Array(r, c) =>
        lazy val row = r.trim.toIntOption
        lazy val col = c.trim.toIntOption
        if !row.isDefined then
          Right(s"Failed to convert row to interger value (got ${r.trim})")
        else if !col.isDefined then
          Right(s"Failed to convert col to interger value (got ${c.trim})")
        else if !isValid(row.get) then
          Right(
            s"Given row was in wrong range (expected from 0 to 2 included and got ${row.get})"
          )
        else if !isValid(col.get) then
          Right(
            s"Given col was in wrong range (expected from 0 to 2 included and got ${col.get})"
          )
        else
          Left(Move(row.get.asInstanceOf[Coord], col.get.asInstanceOf[Coord]))
      case _ =>
        Right(
          "Input does not contain the right amount of commas (only one was expected)"
        )

  private inline def isValid(n: Int): Boolean =
    n >= 0 && n <= 2
