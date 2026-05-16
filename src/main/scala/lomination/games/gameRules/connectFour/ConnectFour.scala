package lomination.games.gameRules.connectFour

import lomination.games.core.gameTypes.{Displayable, Evaluatable, ZeroSumGame}
import lomination.games.core.shared.{Outcome, Score, Turn}

case class ConnectFour(
    turn: Turn = Turn.P1,
    /** Column major! */
    board: Board = IndexedSeq.fill(7)(IndexedSeq.fill(6)(Cell.Empty))
) extends ZeroSumGame[ConnectFour, Move],
      Displayable,
      Evaluatable:

  def isLegal(move: Move): Boolean =
    board(move.c)(0) == Cell.Empty

  def legalMoves: Seq[Move] =
    for {
      c <- 0 to 6
      if board(c)(0) == Cell.Empty
      m = Move(c.asInstanceOf[Coord])
    } yield m
  def move(move: Move): Option[ConnectFour] =
    if !isLegal(move) then None
    else
      Some(
        ConnectFour(
          turn.switch,
          board.updated(
            move.c,
            board(move.c)
              .updated(board(move.c).lastIndexOf(Cell.Empty), Cell.Full(turn))
          )
        )
      )

  def winCombinations: Seq[Seq[Cell]] =
    board
      .flatMap(c => (0 to 2).map(i => c.slice(i, i + 4)))
      .appendedAll(
        board.transpose.flatMap(r => (0 to 3).map(i => r.slice(i, i + 4)))
      )
      .appendedAll(
        for
          c <- 0 to 3
          r <- 0 to 2
        yield (0 to 3).map(i => board(c + i)(r + i))
      )
      .appendedAll(
        for
          c <- 3 to 6
          r <- 0 to 2
        yield (0 to 3).map(i => board(c - i)(r + i))
      )

  def winner: Outcome =
    val win =
      winCombinations
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

  override def eqStates: Seq[ConnectFour] =
    Seq(this, ConnectFour(turn, board.reverse))

  def display: String =
    s"Current turn: ${turn}\n" ++ (0 to 5)
      .map(i =>
        "|" ++ board
          .map(c =>
            c(i) match
              case Cell.Full(Turn.P1) => "⭕"
              case Cell.Full(Turn.P2) => "❌"
              case _                  => "  "
          )
          .mkString("|") ++ "|\n"
      )
      .mkString

  def score: Score =
    Score.Heuristic(
      winCombinations
        .map(_.groupMapReduce(identity)(_ => 1)(_ + _))
        .map(m =>
          (
            m.getOrElse(Cell.Full(turn), 0),
            m.getOrElse(Cell.Full(turn.switch), 0),
            m.getOrElse(Cell.Empty, 0)
          ) match
            case (n, 0, _) => n
            case (0, n, _) => -n
            case _         => 0
        )
        .reduce(_ + _)
    )
