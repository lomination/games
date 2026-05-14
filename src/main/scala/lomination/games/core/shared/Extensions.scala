package lomination.games.core.shared

// Helper to pick a random element if multiple moves have the same score
extension [T](seq: Seq[T])
  def choose: T =
    seq(scala.util.Random.nextInt(seq.size))
