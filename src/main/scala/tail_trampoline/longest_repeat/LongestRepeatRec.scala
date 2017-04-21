package tail_trampoline.longest_repeat

import rx.Observable

/**
  */
case object LongestRepeatRec extends LongestRepeat{
  override def firstLongestRepeat(sequence: CharSequence): Result = if (sequence.length == 0) Result(0, None, 0) else {
    def helper(read: Int): (Result, Result) = {
      if (read == sequence.length()) {
        (Result(0, None, 0), Result(0, None, 0))
      } else {
        val (best, current) = helper(read + 1)
        current.character match {
          case None => (Result(read, Some(sequence.charAt(read)), 1), Result(read, Some(sequence.charAt(read)), 1))
          case Some(c) if c == sequence.charAt(read) =>
            val nextCurrent = Result(read, Some(c), current.length + 1)
            (if (best.length <= nextCurrent.length) nextCurrent else best, nextCurrent)
          case _ => val nextCurrent = Result(read, Some(sequence.charAt(read)), 1)
            (if (best.length <= nextCurrent.length) nextCurrent else best, nextCurrent)
        }
      }

    }
    helper(0)._1
  }

  override def firstLongestRepeat(iterator: Iterator[Char]): Result = firstLongestRepeat(
    Helpers.IteratorToEnumerator(iterator).toEnumerator)

  override def firstLongestRepeat(enumerator: IEnumerator): Result = {
    val monoid = Result.resultMonoid
    def helper(read: Int): (Result, Result) = {
      if (!enumerator.moveNext) {
        (monoid.zero, monoid.zero)
      } else {
        // Call before next iteration as it moves to next!
        val ch = enumerator.current
        val (best, current) = helper(read + 1)
        current.character match {
          case None => (Result(read, Some(ch), 1), Result(read, Some(ch), 1))
          case Some(c) if c == ch =>
            val nextCurrent = Result(read, Some(c), current.length + 1)
            (monoid.append(nextCurrent, best), nextCurrent)
          case _ => val nextCurrent = Result(read, Some(ch), 1)
            (monoid.append(nextCurrent, best), nextCurrent)
        }
      }
    }
    helper(0)._1
  }

//  override def firstLongestRepeat(observable: Observable[Char]): Result = ???
}
