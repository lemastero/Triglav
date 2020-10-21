package Triglav.face3

trait Fnfunctor[F[-_, -_, +_]]
    extends FirstContravariant[F]
    with LeftContrvariant[F]
    with RightFunctor[F]
    with RightProfunctor[F] {

  def fnmap[E, A, R, EE, AA, RR](
      fa: F[E, A, R]
  )(f: EE => E, g: AA => A, h: R => RR): F[EE, AA, RR]

  // derived methods

  override def dimap[E, A, R, EE, RR](
      fa: F[E, A, R]
  )(f: EE => E, h: R => RR): F[EE, A, RR] =
    fnmap(fa)(f, identity, h)

  override def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R] =
    fnmap(fa)(f, identity, identity)

  override def contramapLeft[E, A, R, AA](fa: F[E, A, R])(
      g: AA => A
  ): F[E, AA, R] =
    fnmap(fa)(identity, g, identity)

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] =
    fnmap(fa)(identity, identity, h)
}
