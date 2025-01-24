package Triglav.tambara

trait Polynodal[=>:[-_, +_]]
    extends Closed[=>:]
    with Traversing[=>:] { // TambaraModule D => (List[_],C)
  def griddedL[A, B, C, D]
      : A =>: B => (D => (List[A], C)) =>: (D => (List[B], C)) =
    closed compose strechL
  def griddedR[A, B, C, D]
      : A =>: B => (D => (C, List[A])) =>: (D => (C, List[B])) =
    closed compose strechR
}
