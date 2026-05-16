package lomination.games.gameRules.tictactoe

import lomination.games.core.gameTypes.{Displayable, Evaluatable, ZeroSumGame}
import lomination.games.core.shared.{Outcome, Score, Turn}

case class TicTacToe(
    val turn: Turn = Turn.P1,
    val board: Board = IndexedSeq.fill(3)(IndexedSeq.fill(3)(Cell.Empty))
) extends ZeroSumGame[TicTacToe, Move],
      Displayable,
      Evaluatable:

  def isLegal(move: Move): Boolean =
    board(move.r)(move.c) == Cell.Empty

  def legalMoves: Seq[Move] =
    for {
      r <- 0 to 2
      c <- 0 to 2
      if board(r)(c) == Cell.Empty
      m = Move(r.asInstanceOf[Coord], c.asInstanceOf[Coord])
      // if isLegal(m)
    } yield m
  def move(move: Move): Option[TicTacToe] =
    if !isLegal(move) then None
    else
      Some(
        TicTacToe(
          turn.switch,
          board.updated(
            move.r,
            board(move.r).updated(move.c, Cell.Full(turn))
          )
        )
      )

  def winner: Outcome =
    val win = board.view
      .appendedAll(board.transpose)
      .appended((0 to 2).map(i => board(i)(i)))
      .appended((0 to 2).map(i => board(i)(2 - i)))
      .map(_.distinct)
      .collect { case combi if combi.length == 1 => combi.head }
      .collect { case Cell.Full(turn) => turn }
      .headOption
    if win.isDefined then Outcome.Win(win.get)
    else if legalMoves.isEmpty then Outcome.Draw
    else Outcome.None

  def hash: Int =
    board
      .flatMap(_.map {
        case Cell.Empty         => 0
        case Cell.Full(Turn.P1) => 1
        case _                  => 2
      })
      .foldLeft(0)((acc, x) => acc * 3 + x)

  override def eqStates: Seq[TicTacToe] =
    def rot90(b: Board): Board =
      IndexedSeq.tabulate(3, 3)((r, c) => b(2 - c)(r))

    Seq(
      board,
      board.reverse,
      board.map(_.reverse),
      rot90(board),
      rot90(rot90(board)),
      rot90(rot90(rot90(board))),
      IndexedSeq.tabulate(3, 3)((r, c) => board(c)(r)),
      IndexedSeq.tabulate(3, 3)((r, c) => board(2 - c)(2 - r))
    ).distinct
      .map(b => TicTacToe(turn, b))

  def display: String =
    s"Current turn: ${turn}\n" ++ board
      .map(_.map {
        case Cell.Full(Turn.P1) => "⭕"
        case Cell.Full(Turn.P2) => "❌"
        case _                  => "  "
      }.mkString("|"))
      .mkString("\n--+--+--\n")
      .appended('\n')

  def score: Score = Score.Heuristic(0)
