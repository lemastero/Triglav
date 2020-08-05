package Triglav.cat2

import Triglav.face1.Functor

// Natural transformations
// type f ~> g = forall x. f x -> g x
abstract class ~>[F[_], G[_]] { self =>
  def apply[A](fa: F[A]): G[A]

  def compose[H[_]](other: H ~> F): H ~> G =
    λ[H ~> G]( fa => self(other(fa)) )

  def andThen[H[_]](other: G ~> H): F ~> H =
    λ[F ~> H]( fa => other(self(fa)) )
}

case class IdNat[F[_]]() extends ~>[F,F] {
  def apply[A](fa: F[A]): F[A] = fa
}

object IdNat {
  // you could express idnetity natural transformation as function
  def IdentityNat[F[_]]: F ~> F =
    λ[F ~> F]( fa => identity(fa) )
}

trait NaturalTransformationLaws[F[_], G[_]] extends ~>[F,G] {

  def naturalitySquare[A,B](fa: F[A], ff: F~>G, g: A => B)(implicit FF: Functor[F], FG: Functor[G]): Boolean = {
    val v1: G[A] = ff(fa)
    val v2: G[B] = FG.map(v1)(g)

    val w1: F[B] = FF.map(fa)(g)
    val w2: G[B] = ff(w1)

    v2 == w2
  }
}
