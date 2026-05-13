package lomination.games.core.shared

enum Turn:
  case P1, P2
  def switch: Turn = if this == P1 then P2 else P1
