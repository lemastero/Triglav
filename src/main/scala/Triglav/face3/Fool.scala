package Triglav.face3

trait Fool[F[_, -_, _]] {
  def contramapLeft[E, A, R, AA](fa: F[E, A, R])(g: AA => A): F[E, AA, R]
}
