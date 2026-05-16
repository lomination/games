package lomination.games

import lomination.games.core.ConsoleGameEngine
import lomination.games.core.players.AlphaBeta
import lomination.games.gameRules.tictactoe.{Human, TicTacToe}
// import lomination.games.gameRules.connectFour.{ConnectFour, User}

@main def main(): Unit =
  println(ConsoleGameEngine.run(TicTacToe(), Human(), AlphaBeta(100)))
