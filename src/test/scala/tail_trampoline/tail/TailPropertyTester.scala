package tail_trampoline.tail

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import org.scalacheck.Gen
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatest.{Assertion, Assertions, PropSpecLike}

/**
  * Created by aborg on 22/04/2017.
  */
trait TailPropertyTester extends PropSpecLike with GeneratorDrivenPropertyChecks {
  this: Assertions =>

  protected def implementation: Tail

  protected def testString(str: String, n: Int): Assertion = {
    val parts = str.split("\n", -1)
    //    assert(new String(str.getBytes(StandardCharsets.US_ASCII)) === str)
    //for (i <- 0 to 14) yield {
    val stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.US_ASCII))
    val expected = parts.takeRight(n).toSeq
    //      val startTime = System.nanoTime
    val got = implementation.lastNLines(n, stream)
    //      print(s"\t${(System.nanoTime - startTime) / 1E9}")
    assert(expected === got, new EvalInToString(() => s"$n   ${str.getBytes.toSeq}"))
    //    }
  }


  private val smallGenerator = {
    Gen.oneOf(true, false).flatMap { trim =>
      Gen.choose(0, 40).flatMap(lineNum => {
        Gen.listOfN(lineNum,
          Gen.choose(0, 50).flatMap(lineLength => Gen.listOfN(lineLength, Gen.alphaNumChar).map(_.mkString)))
      }).map(list => list.mkString("", "\n", if (trim) "" else "\n"))
    }
  }
  private val generator = {
    Gen.oneOf(true, false).flatMap { trim =>
      Gen.choose(0, 2000).flatMap(lineNum => {
        Gen.listOfN(lineNum,
          Gen.choose(0, 150).flatMap(lineLength => Gen.listOfN(lineLength, Gen.alphaNumChar).map(_.mkString)))
      }).map(list => list.mkString("", "\n", if (trim) "" else "\n"))
    }
  }

  property("lastNLines property small") {
    forAll(smallGenerator) { (str) => {
      //      whenever(str.forall(c => (c >= ' ' && c <= 'z') || c == '\n')) {
      println(str.length)
      forAll(Gen.chooseNum(0, 14)) { n => {
        whenever(n >= 0) {
          testString(str, n)
        }
      }
      }
      //        }
    }
    }
  }
  property("lastNLines property larger") {
    forAll(generator) { (str) => {
      println(str.length)
      forAll(Gen.chooseNum(0, 14)) { n => {
        testString(str, n)
      }
      }
    }
    }
  }

}

class EvalInToString(val f: () => String) {
  override def toString: String = f()
}
