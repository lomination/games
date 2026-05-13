package lomination.games.core.players

import lomination.games.core.gameTypes.ZeroSumGame
import lomination.games.core.shared.Move

trait Player[G <: ZeroSumGame[G, M], M <: Move]:

  def getMove(game: G): Option[M]
