package tail_trampoline.tail

import java.io.ByteArrayInputStream
import java.nio.charset.StandardCharsets

import org.scalacheck.Gen
import org.scalatest.{Assertion, PropSpec}
import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalactic.TraversableEqualityConstraints

import scala.collection.breakOut

/**
  */
class TailTailRecTest extends TailPropertyTester {
  override protected def implementation: Tail = TailTailRec

}
