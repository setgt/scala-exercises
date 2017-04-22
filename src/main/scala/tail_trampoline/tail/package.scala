package tail_trampoline

/**
  * Task: Implement [[tail_trampoline.tail.Tail]] (last `n` lines) in
  * - [[tail_trampoline.tail.TailTailRec]] (tail recursive)
  * - [[tail_trampoline.tail.TailTrampoline]] (trampoline)
  * It should work for large [[java.io.InputStream]]s too with constant stack size. (ASCII content with `\n` line
  * separators. There is a line before and after each `\n`, `\n`s should not be returned.)
  */
package object tail