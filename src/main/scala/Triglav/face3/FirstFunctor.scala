package Triglav.face3

trait FirstFunctor[F[+_,_,_]] {

  def mapFirst[E, A, R, EE](fa: F[E, A, R])(f: E => EE): F[EE, A, R]
}

trait FirstFunctorLaws[F[+_,_,_]] {
  // TODO laws
}
