package Triglav.cat1

trait FinitelyCoCompleteCategory[Morphism[_,_]] extends Category[Morphism] {

  val Initial: Colimit.InitialObject
  val Coproduct: Colimit.CoProduct

  // TODO pushout
  // TODO coequalizer
}
