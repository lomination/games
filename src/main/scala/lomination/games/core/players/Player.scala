package lomination.games.core.players

import lomination.games.core.gameTypes.ZeroSumGame
import lomination.games.core.shared.Move

trait Player[G <: ZeroSumGame[G, M], M <: Move]:

  /** Note: this function assumes that there is at least one possible move in
    * the current state of the game, i.e. `game.legalMoves` should return a
    * non-empty sequence.
    *
    * @param game
    * @return
    */
  def getMove(game: G): Input[M]
