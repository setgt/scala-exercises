package tail_trampoline.tail

import java.io.InputStream

import scalaz.Free.Trampoline
import scalaz.Trampoline
import scalaz.Trampoline._

/**
  */
object TailTrampoline extends Tail {
  override def lastNLines(n: Int, is: InputStream): Seq[String] =  {
    def helper(): Trampoline[Either[Seq[String], Seq[String]]] = {
      val ch = is.read()
      if (ch < 0) if (n == 0) Trampoline.done(Right(Vector.empty[String])) else Trampoline.done(Left(Vector("")))
      else {
        Trampoline.suspend(helper).flatMap {
         sub => Trampoline.done(sub match
          {
            case result@Right(_) => result
            case Left(seq) if ch == '\n' =>
              if (seq.size == n) Right(seq) else Left("" +: seq)
            case Left(current +: rest) => Left(s"${ch.toChar}$current" +: rest)
          })
        }
      }
    }
    helper().run match {
      case Left(seq) => seq
      case Right(seq) => seq
    }
  }
}
