package lomination.games.gameRules.connectFour

case class Human()
    extends lomination.games.core.players.Human[ConnectFour, Move]:

  def format: String = "col"

  def tryParse(input: String): Either[Move, String] =
    val col = input.trim.toIntOption
    if !col.isDefined then
      Right(s"Failed to convert col to interger value (got ${input.trim})")
    else if !isValid(col.get) then
      Right(
        s"Given col was in wrong range (expected from 0 to 6 included and got ${col.get})"
      )
    else Left(Move(col.get.asInstanceOf[Coord]))

  private inline def isValid(n: Int): Boolean =
    n >= 0 && n <= 2
