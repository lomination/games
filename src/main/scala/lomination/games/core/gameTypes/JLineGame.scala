package lomination.games.core.gameTypes

import lomination.games.core.shared.Move

trait JLineGame[M <: Move]:

  def defaultCursorPos: (Int, Int)

  def onArrowUpPressed(ch: Int, cw: Int): (Int, Int)

  def onArrowDownPressed(ch: Int, cw: Int): (Int, Int)

  def onArrowRightPressed(ch: Int, cw: Int): (Int, Int)

  def onArrowLeftPressed(ch: Int, cw: Int): (Int, Int)

  def coordsToMove(ch: Int, cw: Int): M

  def displayWithCursor(ch: Int, cw: Int): String
