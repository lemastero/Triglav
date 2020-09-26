package Triglav.face3

trait Biffunctor[F[_, +_, +_]] extends Clown[F] with Joker[F] {

  def bimap[E, A, R, AA, RR](
      fa: F[E, A, R]
  )(g: A => AA, h: R => RR): F[E, AA, RR]
}
