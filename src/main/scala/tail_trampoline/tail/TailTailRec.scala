package tail_trampoline.tail

import java.io.InputStream

import scala.annotation.tailrec

object TailTailRec extends Tail {
  override def lastNLines(n: Int, is: InputStream): Seq[String] = {
    @tailrec
    def helper(acc: (Int, Seq[String])): (Int, Seq[String]) = {
      implicit def int2string(i: Int) = if (i < 0) "" else i.toChar.toString
      implicit def bool2int(b: => Boolean) = if (b) 1 else 0
      if (n == 0) (0, Seq[String]())
      else {
        val ch = is.read()
        val nextAcc: (Int, Seq[String]) = acc match {
          case (_, Nil) => (1, Seq(ch))
          case (prevLen, init :+ last) if last.endsWith("\n") =>
            (prevLen + (prevLen < n), (init :+ last.dropRight(1)).drop(!(prevLen < n)) :+ (ch: String))
          case (prevLen, init :+ last) => (prevLen, init :+ last + (ch: String))
        }
        if (ch < 0) nextAcc else helper(nextAcc)
      }
    }
    helper(0, Seq[String]())._2
  }
}
