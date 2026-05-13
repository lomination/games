package lomination.games.gameRules.connectFour

type Coord = 0 | 1 | 2 | 3 | 4 | 5 | 6

case class Move(c: Coord) extends lomination.games.core.shared.Move
