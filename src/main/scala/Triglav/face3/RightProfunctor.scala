package Triglav.face3

trait RightProfunctor[F[-_,_,+_]]
  extends FirstContravariant[F]
  with RightFunctor[F] {

  def dimap[E,A,R,EE,RR](fa: F[E,A,R])(f: EE => E, h: R => RR): F[EE,A,RR]

  // derived methods

  override def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R] =
    dimap(fa)(f, identity)

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] =
    dimap(fa)(identity, h)
}

trait RightProfunctorLaws[F[-_,_,+_]]
  extends FirstContravariantLaws[F]
  with RightFunctorLaws[F]
  with RightProfunctor[F] {

  // TODO
}
