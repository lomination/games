package lomination.games.core.gameTypes

import lomination.games.core.shared._

type Hash = Int

/** G should be the subclass itself and M is the type of the move, that depends
  * on the game.
  */
trait ZeroSumGame[G <: ZeroSumGame[G, M], M <: Move]():

  self: G =>

  val turn: Turn

  def isLegal(move: M): Boolean

  def legalMoves: Seq[M]

  def move(move: M): Option[G]

  def winner: Outcome

  def hash: Hash

  def eqStates: Seq[G] = Seq(this)

  def eqHash: Hash =
    eqStates.map(_.hash).reduce((a, b) => if a <= b then a else b)
