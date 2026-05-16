package lomination.games.core.shared

// Helper to pick a random element if multiple moves have the same score
extension [T](seq: Seq[T])
  def choose: T =
    if seq.isEmpty then
      throw new IllegalStateException("Cannot choose from an empty collection.")
    else seq(scala.util.Random.nextInt(seq.size))
