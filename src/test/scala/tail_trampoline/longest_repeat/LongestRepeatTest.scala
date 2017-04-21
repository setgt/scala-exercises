package tail_trampoline.longest_repeat

import org.scalatest.PropSpecLike
import org.scalatest.prop.TableDrivenPropertyChecks

/**
  */
class LongestRepeatTest extends TableDrivenPropertyChecks with PropSpecLike {
  private val implementations = Table("implementations", LongestRepeatRec, LongestRepeatTailRec, LongestRepeatTrampoline)
  private val implementationsWithEnumerator = implementations.tail.init.tail

  property("concrete examples with CharSequence") {
    forAll(implementations) { impl =>
      assert(impl.firstLongestRepeat("") == Result(0, None, 0))
    }
    forAll(implementations) { impl =>
      assert(impl.firstLongestRepeat("a") == Result(0, Some('a'), 1))
    }
    forAll(implementations) { impl =>
      assert(impl.firstLongestRepeat("ab") == Result(0, Some('a'), 1))
    }
    forAll(implementations) { impl =>
      assert(impl.firstLongestRepeat("aa") == Result(0, Some('a'), 2))
    }
    forAll(implementations) { impl =>
      assert(impl.firstLongestRepeat("miss") == Result(2, Some('s'), 2))
    }
  }
  property("concrete examples with Enumerator") {
    import Helpers._
    forAll(implementationsWithEnumerator) { impl =>
      assert(impl.firstLongestRepeat("".enumerator) == Result(0, None, 0))
    }
    forAll(implementationsWithEnumerator) { impl =>
      assert(impl.firstLongestRepeat("a".enumerator) == Result(0, Some('a'), 1))
    }
    forAll(implementationsWithEnumerator) { impl =>
      assert(impl.firstLongestRepeat("ab".enumerator) == Result(0, Some('a'), 1))
    }
    forAll(implementationsWithEnumerator) { impl =>
      assert(impl.firstLongestRepeat("aa".enumerator) == Result(0, Some('a'), 2))
    }
    forAll(implementationsWithEnumerator) { impl =>
      assert(impl.firstLongestRepeat("miss".enumerator) == Result(2, Some('s'), 2))
    }
  }
}
