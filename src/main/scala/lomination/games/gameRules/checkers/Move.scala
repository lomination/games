package lomination.games.gameRules.checkers

enum Dir:
  case Right, Left

case class Move(r: Int, c: Int, direction: Dir)
    extends lomination.games.core.shared.Move
