package lomination.games

import lomination.games.gameRules.connectFour.{ConnectFour, User}
import lomination.games.core.ConsoleGameEngine
import lomination.games.core.players.BoundedMinimax

@main def main(): Unit =
  println(ConsoleGameEngine.run(ConnectFour(), User(), BoundedMinimax(6)))
