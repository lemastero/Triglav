package Triglav.face3

trait ClownProfunctor[F[-_,+_,_]] extends Bard[F] with Clown[F] {

  def dimapLeft[E,A,R,EE,AA](fa: F[E,A,R])(f: EE => E, g: A => AA): F[EE,AA,R]
}
