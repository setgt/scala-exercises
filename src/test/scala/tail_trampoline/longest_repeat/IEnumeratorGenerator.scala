package tail_trampoline.longest_repeat

/**
  */
object IEnumeratorGenerator {

  def resultFrom(cheatStructure: CheatStructure): Result = {
    val CheatStructure(ints, chars) = cheatStructure
    val lenWithIndex = (0 +: ints.view).zip(ints.view).map { case (prev, next) => next - prev }.view.zipWithIndex
    val (len, firstLongestIndexPlusOne) = lenWithIndex.maxBy(_._1)
    Result(if (firstLongestIndexPlusOne == 0) 0 else ints(firstLongestIndexPlusOne - 1), Some(chars(firstLongestIndexPlusOne)), len)
  }

  def enumeratorFrom(cheatStructure: CheatStructure): IEnumerator = new IEnumerator {
    val CheatStructure(ints, chars) = cheatStructure
    private var pos: Int = -1
    override def moveNext: Boolean = {
      pos += 1
      pos < ints.view.last
    }

    override def current: Char = {
      val idx = java.util.Arrays.binarySearch(ints, pos)
      if (idx >= 0) {
        chars(idx + 1)
      } else {
        chars(-idx - 1)
      }
    }
  }

}
