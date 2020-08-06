package Triglav.cat1

trait FinitelyCompleteCategory[Morphism[_,_]] extends Category[Morphism] {
  type Terminal
  def unit[A]: A => Terminal

  type Initial
  def absurd[A]: Initial => A

  type Product[+A,+B]
  def fst[A,B]: Product[A,B] => A
  def snd[A,B]: Product[A,B] => B

  type Coproduct[+A,+B]
  def left[A,B]: A => Coproduct[A,B]
  def right[A,B]: B => Coproduct[A,B]

  // TODO pullback

  // TODO pushout

  // TODO equalizer

  // TODO coequalizer

  // TODO exponential

  // TODO SubobjectClassifier and Topos

  // TODO NNO and GrothendieckTopos
}
