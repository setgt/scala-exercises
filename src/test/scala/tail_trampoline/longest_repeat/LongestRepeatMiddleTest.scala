package tail_trampoline.longest_repeat

/**
  */
class LongestRepeatMiddleTest extends CheatStructureTests with InputParameterGenerator {
  override protected def minPatterns = 20
  override protected def maxPatterns: Int = 60
  override protected def maxLengthOfSame = 200
}