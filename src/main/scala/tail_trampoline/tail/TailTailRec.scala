package tail_trampoline.tail

import java.io.InputStream

import scala.annotation.tailrec
import scalaz.Dequeue
import scalaz.Maybe.{Empty, Just}

/**
  */
object TailTailRec extends Tail {
  override def lastNLines(n: Int, is: InputStream): Seq[String] = {
    @tailrec
    def helper(acc: Seq[String]): Seq[String] = {
      val ch = is.read()
      if (n == 0) Seq[String]()
      else
      if (ch < 0)
        acc match {
          case Nil => Seq("")
          case head :: Nil => if (head == "\n") Seq("", "").takeRight(n) else head :: Nil
          case head :: tail => if (tail.last == "\n") (head :: tail.init ++ Seq("")).takeRight(n) else head :: tail
        }
      else {
        val nextAcc: Seq[String] =
          if (ch == '\n')
            acc match {
              case Nil => Seq("\n")
              case "\n" :: Nil => Seq("", "\n").takeRight(n)
              case head :: Nil => Seq(head, "\n").takeRight(n)
              case head :: tail =>
                (if (tail.last == "\n") head :: tail.init ++ Seq("", "\n") else head :: tail ++ Seq("\n")).takeRight(n)
            }
          else
            acc match {
              case Nil => Seq(ch.toChar.toString)
              case "\n" :: Nil => Seq("", ch.toChar.toString).takeRight(n)
              case head :: Nil => Seq(head + ch.toChar.toString)
              case head :: tail => (if (tail.last == "\n") head :: tail.init ++ Seq(ch.toChar.toString)
                                   else head :: tail.init ++ Seq(tail.last + ch.toChar.toString)).takeRight(n)
            }

        helper(nextAcc)
      }
    }
    helper(Seq[String]())
  }
}
