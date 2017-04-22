package tail_trampoline.longest_repeat


/**
  */
class LongestRepeatLargeTest extends CheatStructureTests with InputParameterGenerator {
  override protected def minPatterns = 500
  override protected def maxPatterns: Int = 6000
  override protected def maxLengthOfSame = 20000

  override protected def implementationToTest: LongestRepeat = LongestRepeatTailRec
}
