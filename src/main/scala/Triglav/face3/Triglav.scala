package Triglav.face3

trait Triglav[F[-_,+_,+_]]
  extends Bard[F] with Clown[F] with Joker[F]
    with Biffunctor[F]
    with ClownProfunctor[F] with JokerProfunctor[F] {

  def timap[E,A,R,EE,AA,RR](fa: F[E,A,R])(
    f: EE => E,
    g: A => AA,
    h: R => RR): F[EE,AA,RR]
}
