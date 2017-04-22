package tail_trampoline.tail

/**
  */
class TailTrampolineTest extends TailPropertyTester {
  override protected def implementation: Tail = TailTrampoline

}
