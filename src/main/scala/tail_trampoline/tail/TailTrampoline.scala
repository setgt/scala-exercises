package tail_trampoline.tail

import java.io.InputStream

import scalaz.Free.Trampoline
import scalaz.Trampoline._

/**
  */
object TailTrampoline extends Tail {
  override def lastNLines(n: Int, is: InputStream): Seq[String] = ???
}
