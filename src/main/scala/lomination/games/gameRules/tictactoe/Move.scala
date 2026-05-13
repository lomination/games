package lomination.games.gameRules.tictactoe

type Coord = 0 | 1 | 2

case class Move(r: Coord, c: Coord) extends lomination.games.core.shared.Move
