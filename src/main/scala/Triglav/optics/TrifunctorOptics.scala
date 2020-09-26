package Triglav.optics

import Triglav.face3.{Nifunctor, Trifunctor}
import Triglav.face2.Profunctor
import Triglav.optics.ProfunctorOptics.Optics

object TrifunctorOptics {

  def trifunctorGet[P[-_, +_], B](implicit PP: Profunctor[P]) = {
    type TriOptic[-S, +T, +A] = Optics[P, S, T, A, B]

    new Trifunctor[TriOptic] {
      override def timap[E, A, R, EE, AA, RR](
          fa: P[R, B] => P[E, A]
      )(f: EE => E, g: A => AA, h: R => RR): P[RR, B] => P[EE, AA] =
        rrb => {
          val r1: P[R, B] = PP.contramap(rrb)(h)
          val r2: P[E, A] = fa(r1)
          PP.dimap(r2)(f, g)
        }
    }
  }

  def trifunctorSet[P[-_, +_], A](implicit PP: Profunctor[P]) = {
    type TriOptic[-S, -B, +T] = Optics[P, S, T, A, B]

    new Nifunctor[TriOptic] {
      override def nimap[E, A1, R, EE, AA, RR](
          fa: P[A, A1] => P[E, R]
      )(f: EE => E, g: AA => A1, h: R => RR): P[A, AA] => P[EE, RR] =
        a => {
          val r1: P[A, A1] = PP.map(a)(g)
          val r2: P[E, R] = fa(r1)
          PP.dimap(r2)(f, h)
        }
    }
  }
}
