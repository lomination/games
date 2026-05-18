package lomination.games

import lomination.games.core.engines.JLineConsoleGameEngine
import lomination.games.core.players.AlphaBeta
import lomination.games.gameRules.tictactoe.{JLineHuman, TicTacToe}
// import lomination.games.gameRules.connectFour.{ConnectFour, User}

@main def main(): Unit =
  println(JLineConsoleGameEngine.run(TicTacToe(), JLineHuman(), AlphaBeta(100)))
