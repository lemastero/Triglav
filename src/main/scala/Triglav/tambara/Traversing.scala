package Triglav.tambara

import Triglav.face2.Profunctor

trait Traversing[=>:[-_, +_]]
    extends Profunctor[=>:] { // TambaraModule (List[_],C)
  def strechL[A, B, C]: (
      A =>: B
  ) => (List[A], C) =>: (List[B], C) // TODO replace List by Traverse
  def strechR[A, B, C]: (A =>: B) => (C, List[A]) =>: (C, List[B])
}
