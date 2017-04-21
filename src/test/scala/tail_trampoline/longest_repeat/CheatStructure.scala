package tail_trampoline.longest_repeat

/**
  */
case class CheatStructure(ints: Array[Int], chars: Array[Char]) {
  struct =>
  require(chars.length == ints.length + 1)//Implicit 0 before the ints
  override def toString: String = {
    s"""${ints.mkString(",")}\n\n${chars.mkString(" ")}"""
  }

  def toImmutableCharSequence: CharSequence = new CharSequence {
    outer =>
    override def length: Int = ints(ints.length - 1)

    override def subSequence(start: Int, end: Int): CharSequence = new CharSequence {
      override def length(): Int = end - start

      override def subSequence(startSub: Int, endSub: Int): CharSequence = outer.subSequence(start +startSub,
        start + endSub)

      override def charAt(index: Int): Char = outer.charAt(index + start)
    }

    override def charAt(index: Int): Char = {
      val idx = java.util.Arrays.binarySearch(ints, index)
      val chs: Array[Char] = struct.chars
      if (idx >= 0) {
        chs(idx + 1)
      } else {
        chs(-idx - 1)
      }
    }

    override def toString: String = ???
  }
}
