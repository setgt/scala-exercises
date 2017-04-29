package tail_trampoline.longest_repeat

import rx.Observable

import scala.annotation.tailrec

/**
  */
case object LongestRepeatTailRec extends LongestRepeat {
  private val resultMonoid = Result.resultMonoid

  override def firstLongestRepeat(sequence: CharSequence): Result = {
    firstLongestRepeat(Helpers.CharSequenceAsIEnumerator(sequence).enumerator)
  }

  import Helpers._
  override def firstLongestRepeat(iterator: Iterator[Char]): Result = firstLongestRepeat(iterator.toEnumerator)

//  override def firstLongestRepeat(observable: Observable[Char]): Result = ???

  override def firstLongestRepeat(enumerator: IEnumerator): Result = {
    val monoid = Result.resultMonoid
    @tailrec
    def helper(read: Int, bestPar: Result, currentPar: Result): (Result, Result) = {
      if (!enumerator.moveNext) {
        (bestPar, currentPar)
      } else {
        val forwardPar = {
          // Call before next iteration as it moves to next!
          val ch = enumerator.current
          //val (best, current) = helper(read + 1)
          currentPar.character match {
            case None =>
              (Result(read, Some(ch), 1), Result(read, Some(ch), 1))
            case Some(c) if c == ch =>
              val nextCurrent = Result(currentPar.firstPosition, Some(c), currentPar.length + 1)
              (monoid.append(nextCurrent, bestPar), nextCurrent)
            case _ =>
              val nextCurrent = Result(read, Some(ch), 1)
              (monoid.append(nextCurrent, bestPar), nextCurrent)
          }
        }
        helper(read + 1, forwardPar._1, forwardPar._2)
      }
    }
    helper(0, monoid.zero, monoid.zero)._1
  }
}
