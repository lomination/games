package lomination.games.core.players

enum Input[+M <: lomination.games.core.shared.Move]:
  case Move(move: M)
  case Quit
