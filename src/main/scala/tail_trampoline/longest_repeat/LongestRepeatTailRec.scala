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

  override def firstLongestRepeat(enumerator: IEnumerator): Result = ???
}
