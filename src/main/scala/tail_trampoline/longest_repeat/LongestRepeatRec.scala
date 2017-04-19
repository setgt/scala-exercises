package tail_trampoline.longest_repeat

/**
  */
object LongestRepeatRec extends LongestRepeat{
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

  override def firstLongestRepeat(iterator: Iterator[Char]): Result = ???

  override def firstLongestRepeat(enumerator: IEnumerator): Result = ???
}
