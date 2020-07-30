package Triglav.face3

trait Clown[F[_,+_,_]] {
  def mapLeft[E,A,R,AA](fa: F[E,A,R])(g: A => AA): F[E,AA,R]
}
