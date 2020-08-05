package Triglav.face3

trait Trifunctor[F[-_,+_,+_]]
  extends Bard[F] with Clown[F] with Joker[F]
    with Biffunctor[F]
    with ClownProfunctor[F] with JokerProfunctor[F] {

  def timap[E,A,R,EE,AA,RR](fa: F[E,A,R])(
    f: EE => E,
    g: A => AA,
    h: R => RR): F[EE,AA,RR]

  override def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R] =
    timap(fa)(f, identity[A], identity[R])

  override def mapLeft[E, A, R, AA](fa: F[E, A, R])(g: A => AA): F[E, AA, R] =
    timap(fa)(identity[E], g, identity[R])

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] =
    timap(fa)(identity[E], identity[A], h)

  override def bimap[E, A, R, AA, RR](fa: F[E, A, R])(g: A => AA, h: R => RR): F[E, AA, RR] =
    timap(fa)(identity[E], g, h)

  override def dimapLeft[E, A, R, EE, AA](fa: F[E, A, R])(f: EE => E, g: A => AA): F[EE, AA, R] =
    timap(fa)(f, g, identity[R])

  override def dimap[E, A, R, EE, RR](fa: F[E, A, R])(f: EE => E, h: R => RR): F[EE, A, RR] =
    timap(fa)(f, identity[A], h)
}
