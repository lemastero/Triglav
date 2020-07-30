package Triglav.wip

/** Natural transformation for profunctors */
abstract class ~~>[F[_,_], G[_,_]] { self =>
  def apply[A,B](fa: F[A,B]): G[A,B]

  def andThen[H[_,_]](other: G ~~> H): F ~~> H =
  // λ[F ~~> H]( fa => other(self(fa)) ) dont work :( check docs maybe PR/Issue
    new ~~>[F,H] {
      def apply[A,B](fa: F[A,B]): H[A,B] = other(self(fa))
    }

  def compose[H[_,_]](other: H ~~> F): H ~~> G =
    new ~~>[H,G] {
      def apply[A,B](fa: H[A,B]): G[A,B] = self(other(fa))
    }
}
