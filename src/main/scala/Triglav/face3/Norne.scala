package Triglav.face3

trait Norne[F[-_,-_,+_]]
  extends Bard[F] with Fool[F] with Joker[F] {

  def nimap[E,A,R,EE,AA,RR](fa: F[E,A,R])(
    f: EE => E,
    g: AA => A,
    h: R => RR): F[EE,AA,RR]
}
