package Triglav.face3

trait LeftProfunctor[F[-_, +_, _]]
    extends FirstContravariant[F]
    with LeftFunctor[F] {

  def dimapLeft[E, A, R, EE, AA](
      fa: F[E, A, R]
  )(f: EE => E, g: A => AA): F[EE, AA, R]
}

trait LeftProfunctorLaw[F[-_, +_, _]]
    extends FirstContravariantLaws[F]
    with LeftFunctorLaws[F] {
  // TODO
}
