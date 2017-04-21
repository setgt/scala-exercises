package tail_trampoline.tail

import java.io.InputStream

import scala.annotation.tailrec
import scalaz.Dequeue
import scalaz.Maybe.{Empty, Just}

/**
  */
object TailTailRec extends Tail {
  override def lastNLines(n: Int, is: InputStream): Seq[String] = ???
}
