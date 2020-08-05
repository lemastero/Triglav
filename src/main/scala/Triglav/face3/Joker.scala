package Triglav.face3

trait Joker[F[_,_,+_]] {
  def map[E,A,R,RR](fa: F[E,A,R])(h: R => RR): F[E,A,RR]
}
