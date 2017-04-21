package tail_trampoline.longest_repeat

import rx.Observable

import scala.util.control.TailCalls._

/**
  */
case object LongestRepeatTrampoline extends LongestRepeat {

  override def firstLongestRepeat(sequence: CharSequence): Result = ???

  import Helpers._
  override def firstLongestRepeat(iterator: Iterator[Char]): Result = firstLongestRepeat(iterator.toEnumerator)

//  override def firstLongestRepeat(observable: Observable[Char]): Result = ???

  override def firstLongestRepeat(enumerator: IEnumerator): Result = ???
}
