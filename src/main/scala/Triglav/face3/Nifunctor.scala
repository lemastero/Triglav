package Triglav.face3

trait Nifunctor[F[-_, -_, +_]] extends Bard[F] with Fool[F] with Joker[F] {

  def nimap[E, A, R, EE, AA, RR](
      fa: F[E, A, R]
  )(f: EE => E, g: AA => A, h: R => RR): F[EE, AA, RR]

  override def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R] =
    ???

  override def contramapLeft[E, A, R, AA](fa: F[E, A, R])(
      g: AA => A
  ): F[E, AA, R] = ???

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] = ???
}
