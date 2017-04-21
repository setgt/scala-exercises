package tail_trampoline.tail

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import org.scalatest.{Assertion, PropSpec}
import org.scalatest.prop.TableDrivenPropertyChecks
import org.scalactic._
import org.scalactic.TraversableEqualityConstraints

/**
  */
class TailTest extends PropSpec with TableDrivenPropertyChecks with TraversableEqualityConstraints {
  private val implementations = Table("implementations", TailRec, TailTailRec, TailTrampoline)
  private val concreteExamples = Table(("n", "content", "expected"),
    (0, "", Seq.empty[String]),
    (0, "\n", Seq.empty[String]),
    (0, "a", Seq.empty[String]),
    (0, "Hello\nWorld\n", Seq.empty[String]),
    (1, "", Seq("")),
    (1, "\n", Seq("")),
    (1, "a", Seq("a")),
    (1, "x\na\nb", Seq("b")),
    (1, "Hello\nWorld\n", Seq("")),
    (2, "", Seq("")),
    (2, "\n", Seq("", "")),
    (2, "a", Seq("a")),
    (2, "Hello\nWorld\n", Seq("World",""))
  )

  private def testString(impl: Tail, n: Int, s: String, expected: Seq[String]): Assertion = {
    assert(impl.lastNLines(n, new ByteArrayInputStream(s.getBytes(StandardCharsets.US_ASCII))) === expected, {
      val input = s.replaceAll("\n", "`")
      val exp = expected.map(s => s"|$s|").mkString
      s"$input expected: $exp"})
  }

  property("concrete examples lastNLines property") {
    forAll(implementations) { impl =>
      forAll(concreteExamples) { (n: Int, s: String, seq: Seq[String]) =>
        testString(impl, n, s, seq)
      }
    }
  }

}
