package Triglav.tambara

import Triglav.face2.ProfunctorLaws

trait Glassed[P[-_, +_]]
    extends CartesianStrong[P]
    with Closed[P] { // TambaraModule (T, U => _)

  def glassedL[A, B, U, T]: P[A, B] => P[(T, U => A), (T, U => B)] = {
    second[U => A, U => B, T] compose closed[A, B, U]
  }

  def glassedR[A, B, U, T]: P[A, B] => P[(U => A, T), (U => B, T)] = {
    first[U => A, U => B, T] compose closed[A, B, U]
  }
}

trait GlassedLaws[P[-_, +_]] extends Glassed[P] with ProfunctorLaws[P] {

  // TODO
}
