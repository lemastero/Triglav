package Triglav.face3

trait LeftBifunctor[F[+_, +_, _]] extends FirstFunctor[F] with LeftFunctor[F] {

  def bimapLeft[E, A, R, EE, AA](
      fa: F[E, A, R]
  )(f: E => EE, g: A => AA): F[EE, AA, R]

  // derived methods
  override def mapFirst[E, A, R, EE](fa: F[E, A, R])(f: E => EE): F[EE, A, R] =
    bimapLeft(fa)(f, identity[A])

  override def mapLeft[E, A, R, AA](fa: F[E, A, R])(g: A => AA): F[E, AA, R] =
    bimapLeft(fa)(identity[E], g)
}

trait LeftBifunctorLaws[F[+_, +_, _]]
    extends FirstFunctorLaws[F]
    with LeftFunctorLaws[F] {
  // TODO
}
