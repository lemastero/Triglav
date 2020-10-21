package Triglav.face3

trait LeftContrvariant[F[_, -_, _]] {
  def contramapLeft[E, A, R, AA](fa: F[E, A, R])(g: AA => A): F[E, AA, R]
}

trait LeftContrvariantLaws[T[_, -_, _]] {
  // TODO
}
