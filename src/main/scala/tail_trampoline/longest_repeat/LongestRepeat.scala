package tail_trampoline.longest_repeat

import scala.collection.TraversableOnce

/**
  * @param firstPosition `0`-based position within the input.
  * @param character The repeating character. ([[None]] in case input was empty)
  * @param length The length of repeat.
  */
case class Result(firstPosition: Int, character: Option[Char], length: Int)

/**
  * Specialized on [[Char]], inspired by .NET's IEnumerator.
  */
trait IEnumerator {
  def current: Char
  def moveNext: Boolean
}

/**
  * The longest repeating sequence [[Char]] computer.
  */
trait LongestRepeat {
  def firstLongestRepeat(sequence: CharSequence): Result = firstLongestRepeat(sequence.toString.to[TraversableOnce])
  def firstLongestRepeat(traversableOnce: TraversableOnce[Char]): Result = firstLongestRepeat(traversableOnce.toIterator)
  def firstLongestRepeat(iterator: Iterator[Char]): Result
  def firstLongestRepeat(enumerator: IEnumerator): Result
}
