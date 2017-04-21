package tail_trampoline.longest_repeat

import rx.Observable

import scala.collection.TraversableOnce
import scalaz.Monoid

/**
  * @param firstPosition `0`-based position within the input.
  * @param character The repeating character. ([[None]] in case input was empty)
  * @param length The length of repeat.
  */
case class Result(firstPosition: Int, character: Option[Char], length: Int)

object Result {
  implicit val resultMonoid = new Monoid[Result] {
    override def zero: Result = Result(0, None, 0)

    override def append(f1: Result, f2: => Result): Result = {
      val f2Strict = f2
      if (f1.length > f2Strict.length) {
        f1
      } else if (f2Strict.length > f1.length) {
        f2
      } else if (f1.firstPosition < f2.firstPosition) {
        f1
      } else {
        f2Strict
      }
    }
  }
}

/**
  * Specialized on [[Char]], inspired by .NET's IEnumerator.
  */
trait IEnumerator {
  def current: Char
  def moveNext: Boolean
}
object Helpers {
  implicit class CharSequenceAsIEnumerator(cs: CharSequence) {
    def enumerator: IEnumerator = new IEnumerator {
      private var pos = -1

      override def moveNext: Boolean = {
        pos += 1
        pos < cs.length
      }

      override def current: Char = cs.charAt(pos)
    }
  }

  implicit class EnumeratorToIterator(enumerator: IEnumerator) {
    def iterator: Iterator[Char] = new Iterator[Char] {
      private var hasNextValue = false
      private var calledNext = false
      private var calledHasNext = false
      override def hasNext: Boolean = {
        if (!calledHasNext) {
          hasNextValue = enumerator.moveNext
        }
        hasNextValue
      }

      override def next(): Char = {
        val next = enumerator.current
        hasNextValue = enumerator.moveNext
        calledNext = false
        calledHasNext = true
        next
      }
    }
  }

  implicit class CharSequenceToIterator(cs: CharSequence) {
    def toIterator: Iterator[Char] = new Iterator[Char] {
      private var pos = 0
      override def hasNext: Boolean = pos < cs.length

      override def next(): Char = {
//        require(hasNext)
        val res = cs.charAt(pos)
        pos += 1
        res
      }
    }
  }

  /** UNTESTED!!! */
  implicit class IteratorToEnumerator(it: Iterator[Char]) {
    def toEnumerator: IEnumerator = new IEnumerator {
      private var hasNextValue = it.hasNext
      private var next: Char = _
      override def moveNext: Boolean = {
        val res = hasNextValue
        next = it.next()
        hasNextValue = it.hasNext
        res
      }

      override def current: Char = next
    }
  }
//  implicit class EnumeratorToObservable(enumerator: IEnumerator) {
//    def observable: Observable[Char] = new Observable[Char]()
//  }
}

/**
  * The longest repeating sequence [[Char]] computer.
  */
trait LongestRepeat {
  def firstLongestRepeat(sequence: CharSequence): Result = firstLongestRepeat(sequence.toString.to[TraversableOnce])
  def firstLongestRepeat(traversableOnce: TraversableOnce[Char]): Result = firstLongestRepeat(traversableOnce.toIterator)
  def firstLongestRepeat(iterator: Iterator[Char]): Result
//  def firstLongestRepeat(observable: Observable[Char]): Result
  def firstLongestRepeat(enumerator: IEnumerator): Result
}
