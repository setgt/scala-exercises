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
    def helper(acc: (Int, Seq[String])): (Int, Seq[String]) = {
      val ch = is.read()
      if (n == 0) (0, Seq[String]())
      else
      if (ch < 0)
        acc match {
          case (prevLen, Nil) => (1, Seq(""))
          case (prevLen, head :: Nil) =>
            if (head == "\n")
              if (prevLen < n) (2,(Seq("", ""))) else (1, Seq(""))
            else (1, head :: Nil)
          case (prevLen, head :: tail) => if (tail.last == "\n") (prevLen, (head :: tail.init ++ Seq("")))
                                          else (prevLen, head :: tail)
        }
      else {
        val nextAcc: (Int, Seq[String]) =
          if (ch == '\n')
            acc match {
              case (_,Nil) => (1, Seq("\n"))
              case (prevLen, "\n" :: Nil) => if (prevLen < n) (prevLen + 1 , Seq("", "\n")) else (n, Seq("\n"))
              case (prevLen, head :: Nil) => if (prevLen < n) (prevLen + 1, Seq(head, "\n")) else (n, Seq("\n"))
              case (prevLen, head :: tail) =>
                if (tail.last == "\n")
                     if (prevLen < n) (prevLen + 1, head :: tail.init ++ Seq("", "\n")) else (n, tail.init ++ Seq("", "\n"))
                else
                     if (prevLen < n) (prevLen + 1, head :: tail ++ Seq("\n")) else (n, tail ++ Seq("\n"))
            }
          else
            acc match {
              case (_, Nil) => (1, Seq(ch.toChar.toString))
              case (_, "\n" :: Nil) => if (n > 1) (2, Seq("", ch.toChar.toString)) else (1, Seq(ch.toChar.toString))
              case (_, head :: Nil) => (1, Seq(head + ch.toChar.toString))
              case (prevLen, head :: tail) =>
                if (tail.last == "\n")
                  (prevLen, head :: tail.init ++ Seq(ch.toChar.toString))
                else
                  (prevLen, head :: tail.init ++ Seq(tail.last + ch.toChar.toString))
            }

        helper(nextAcc)
      }
    }
    helper(0, Seq[String]())._2
  }
}
