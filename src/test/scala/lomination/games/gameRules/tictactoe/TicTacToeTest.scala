package lomination.games.gameRules.tictactoe

import lomination.games.core.shared.Outcome
import lomination.games.core.shared.Turn.{P1, P2}

import Cell.{Empty, Full}

class TicTacToeTest extends munit.FunSuite {
  test("winner: ensure draw") {
    val game = TicTacToe(
      turn = P1,
      board = IndexedSeq(
        IndexedSeq(Full(P1), Full(P2), Empty),
        IndexedSeq(Empty, Full(P2), Full(P2)),
        IndexedSeq(Full(P1), Empty, Full(P1))
      )
    )
    val obtained = game.winner
    val expected = Outcome.None
    assertEquals(obtained, expected)
  }

  test("winner: ensure win") {
    val game = TicTacToe(
      turn = P1,
      board = IndexedSeq(
        IndexedSeq(Full(P1), Full(P2), Empty),
        IndexedSeq(Empty, Full(P2), Full(P2)),
        IndexedSeq(Full(P1), Full(P1), Full(P1))
      )
    )
    val obtained = game.winner
    val expected = Outcome.Win(P1)
    assertEquals(obtained, expected)
  }

  test("winner: ensure win") {
    val game = TicTacToe(
      turn = P1,
      board = IndexedSeq(
        IndexedSeq(Full(P2), Full(P2), Empty),
        IndexedSeq(Empty, Full(P2), Full(P2)),
        IndexedSeq(Full(P1), Full(P1), Full(P2))
      )
    )
    val obtained = game.winner
    val expected = Outcome.Win(P2)
    assertEquals(obtained, expected)
  }

}
