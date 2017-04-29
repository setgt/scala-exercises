package tail_trampoline.longest_repeat

import rx.Observable

import scala.util.control.TailCalls._
import scalaz.Free.Trampoline
import scalaz.Trampoline

/**
  */
case object LongestRepeatTrampoline extends LongestRepeat {

  override def firstLongestRepeat(sequence: CharSequence): Result = firstLongestRepeat(Helpers.CharSequenceAsIEnumerator(sequence).enumerator)

  import Helpers._
  override def firstLongestRepeat(iterator: Iterator[Char]): Result = firstLongestRepeat(iterator.toEnumerator)

//  override def firstLongestRepeat(observable: Observable[Char]): Result = ???

  override def firstLongestRepeat(enumerator: IEnumerator): Result =  {
    val monoid = Result.resultMonoid
    def helper(read: Int): Trampoline[(Result, Result)] = {
      if (!enumerator.moveNext) {
        Trampoline.done((monoid.zero,monoid.zero))
      } else {
        // Call before next iteration as it moves to next!
        val ch = enumerator.current
        Trampoline.suspend(helper(read + 1)).flatMap { case (best: Result, current: Result) =>
          Trampoline.done {
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
      }
    }
    helper(0).run._1
  }
}
