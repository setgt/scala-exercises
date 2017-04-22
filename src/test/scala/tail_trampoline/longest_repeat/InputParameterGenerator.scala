package tail_trampoline.longest_repeat

import org.scalacheck.Gen
import org.scalatest.PropSpecLike
import org.scalatest.prop.GeneratorDrivenPropertyChecks

import scala.util.Random

object Constants {
  val firstChar: Char = ' '
  val charDomainSize = 80
}

/**
  */
trait InputParameterGenerator {
  protected def minPatterns: Int = 2
  protected def maxPatterns: Int = 3

  protected def maxLengthOfSame: Int = 2000

  private val numberOfChanges = Gen.choose(minPatterns, maxPatterns)
  private val asciisIndex = Gen.choose(0, Constants.charDomainSize)
  protected val structureGenerator: Gen[CheatStructure] = for (n <- numberOfChanges) yield {
    val res = CheatStructure(new Array[Int](n), new Array[Char](n + 1))
    val CheatStructure(ints, chars) = res
    chars(0) = 'x'
    for {i <- 0 until n
         pos = Random.nextInt(maxLengthOfSame) + 1
         idx = Random.nextInt(Constants.charDomainSize - 1/*Previous cannot be selected*/) + 1
    } {
      ints(i) = (if (i == 0) 0 else ints(i - 1)) + pos
      chars(i + 1) = (((chars(i) - Constants.firstChar) + idx) % Constants.charDomainSize + Constants.firstChar).toChar
    }
    res
  }

}

trait CheatStructureTests extends PropSpecLike with GeneratorDrivenPropertyChecks with InputParameterGenerator {
  protected def implementationToTest: LongestRepeat

  property(s"${implementationToTest.getClass.getSimpleName} should handle large IEnumerables") {
    forAll(structureGenerator) {case struct @ CheatStructure(ints, _) =>
      println(ints.last)
      assert(IEnumeratorGenerator.resultFrom(struct) ===
        implementationToTest.firstLongestRepeat(IEnumeratorGenerator.enumeratorFrom(struct)))
    }
  }

  property(s"${implementationToTest.getClass.getSimpleName} should handle large CharSequences") {
    forAll(structureGenerator) {case struct @ CheatStructure(ints, _) =>
      println(ints.last)
      assert(IEnumeratorGenerator.resultFrom(struct) ===
        implementationToTest.firstLongestRepeat(struct.toImmutableCharSequence))
    }
  }

  import Helpers._
  property(s"${implementationToTest.getClass.getSimpleName} should handle large Iterables") {
    forAll(structureGenerator) {case struct @ CheatStructure(ints, _) =>
      println(ints.last)
      assert(IEnumeratorGenerator.resultFrom(struct) ===
        implementationToTest.firstLongestRepeat(struct.toImmutableCharSequence.toIterator))
    }
  }

}
