package tail_trampoline.tail

import java.io.{IOException, InputStream}

/**
  * Similar to the UNIX `tail` command, returns the last n lines of a stream.
  */
trait Tail {
  /**
    * Computes the last `n` lines of `is` ASCII input stream.
    *
    * @param n
    * @param is
    * @throws java.io.IOException
    * @return The last `n` lines.
    */
  @throws(classOf[IOException])
  def lastNLines(n: Int, is: InputStream): Seq[String]
}
