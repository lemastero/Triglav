package Triglav.face3

// laws in each case are similar like in versions with 2 holes
trait Bard[F[-_,_,_]] {
  def contramap[E,A,R,EE](fa: F[E,A,R])(f: EE => E): F[EE,A,R]
}
