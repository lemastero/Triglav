package Triglav.tambara

trait Telescoped[=>:[-_, +_]]
    extends Closed[=>:]
    with CoCartesianChoice[=>:] { // TambaraModule Either(C => _, D)
  def telescopeL[A, B, C, D]
      : (A =>: B) => (Either[C => A, D] =>: Either[C => B, D]) =
    left compose closed
  def telescopeR[A, B, C, D]
      : (A =>: B) => (Either[D, C => A] =>: Either[D, C => B]) =
    right compose closed
}
