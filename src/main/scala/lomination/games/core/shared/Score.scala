package lomination.games.core.shared

enum Score extends Ordered[Score]:

  /** Max value. Can be considered as +inf. */
  case Win

  /** Null value. Can be considered as 0. */
  case Draw

  /** Min value. Can be considered as -inf. */
  case Loose

  /** Integer value corresponding to s. */
  case Heuristic(score: Int)

  def compare(that: Score): Int = (this, that) match
    case (Win, Win) => 0
    case (Win, _)   => 1
    case (_, Win)   => -1

    case (Loose, Loose) => 0
    case (Loose, _)     => -1
    case (_, Loose)     => 1

    case (Heuristic(s1), Heuristic(s2)) => s1.compare(s2)
    case (Heuristic(s), Draw)          => s
    case (Draw, Heuristic(s))          => -s
    case (Draw, Draw)                 => 0

  def unary_- : Score = this match
    case Win         => Loose
    case Draw        => Draw
    case Loose       => Win
    case Heuristic(s) => Heuristic(-s)

// object Score {
//   given Conversion[Int, Score] with {
//     def apply(n: Int): Score = Score.Heuristic(n)
//   }
// }
