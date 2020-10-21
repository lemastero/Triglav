package Triglav.face4

object Optics {
  type ProOptic[P[-_, +_], S, T, A, B] = P[A, B] => P[S, T]
}
