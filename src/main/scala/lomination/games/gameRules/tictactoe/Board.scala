package lomination.games.gameRules.checkers

import lomination.games.core.shared.Turn

enum Cell:
  case Full(Turn: Turn)
  case Empty

type Board = IndexedSeq[IndexedSeq[Cell]]
