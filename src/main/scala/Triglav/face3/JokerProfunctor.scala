package Triglav.face3

trait JokerProfunctor[F[-_, _, +_]] extends Bard[F] with Joker[F] {

  def dimap[E, A, R, EE, RR](
      fa: F[E, A, R]
  )(f: EE => E, h: R => RR): F[EE, A, RR]
}
