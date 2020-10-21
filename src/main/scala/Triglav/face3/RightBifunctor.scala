package Triglav.face3

trait RightBifunctor[F[_, +_, +_]] extends LeftFunctor[F] with RightFunctor[F] {

  def bimap[E, A, R, AA, RR](
      fa: F[E, A, R]
  )(g: A => AA, h: R => RR): F[E, AA, RR]

  // derived methods

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] =
    bimap(fa)(identity, h)

  override def mapLeft[E, A, R, AA](fa: F[E, A, R])(g: A => AA): F[E, AA, R] =
    bimap(fa)(g, identity)
}

trait RightBifunctorLaws[F[_, +_, +_]]
    extends LeftFunctorLaws[F]
    with RightFunctorLaws[F] {
  // TODO
}
