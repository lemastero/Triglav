package Triglav.cat2

trait FinitelyComplete2Category[Morphism[_[_], _[_]]]
    extends TwoCategory[Morphism] {

  type Terminal[A]
  def unit[A[_]]: A ~> Terminal

  type Initial[_]
  def absurd[A[_]]: Initial ~> A
}
