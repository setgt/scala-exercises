package tail_trampoline.tail

import java.io.InputStream

/**
  */
case object TailRec extends Tail {
  override def lastNLines(n: Int, is: InputStream): Seq[String] = {
    def helper(): Either[Seq[String], Seq[String]] = {
      val ch = is.read()
      if (ch < 0) if (n == 0) Right(Vector.empty[String]) else Left(Vector(""))
      else {
        val lines = helper()
        lines match {
          case result @ Right(_) => result
          case Left(seq) if ch == '\n' =>
            if (seq.size == n) Right(seq) else Left("" +: seq)
          case Left(current +: rest) => Left(s"${ch.toChar}$current" +: rest)
        }
      }
    }
    helper() match {
      case Left(seq) => seq
      case Right(seq) => seq
    }
  }
}
