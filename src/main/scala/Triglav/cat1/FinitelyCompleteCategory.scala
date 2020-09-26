package Triglav.cat1

trait FinitelyCompleteCategory[Morphism[_, _]] extends Category[Morphism] {

  val Terminal: Limit.TerminalObject
  val Product: Limit.Product

  // TODO pullback

  // TODO equalizer

  // TODO exponential
}
