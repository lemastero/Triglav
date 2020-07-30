package Triglav.face3

trait Joker[F[_,_,+_]] {
  def map[A,E,R,RR](fa: F[A,E,R])(h: R => RR): F[A,E,RR]
}
