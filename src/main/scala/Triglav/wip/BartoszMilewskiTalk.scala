package Triglav.wip

import Triglav.face1.Functor
import Triglav.wip.IdNat.IdentityNat
import Triglav.wip.ProReader.ProReader

class BartoszMilewskiTalk {
  trait Profunctor[P[_,_]] {
    def dimap[S,T,A,B](sa: S=>A, bt: B=>T): P[A,B] => P[S,T]
  }

  trait Strong[P[_,_]] extends Profunctor[P] {
    def first[A,B,C]:  P[A,B] => P[(A,C),(B,C)]
  }

  trait Choice[P[_,_]] extends Profunctor[P] {
    def left[A,B,C]: P[A,B] => P[Either[A,C], Either[B,C]]
  }

  // type Lens s t a b = forall p. Strong p => p a b -> p s t
  abstract class Lens[S,T,A,B] {
    def apply[P[_,_] : Strong]: P[A,B] => P[S,T]
  }

  abstract class Prism[S,T,A,B] {
    def apply[P[_,_] : Choice]: P[A,B] => P[S,T]
  }

  type Reader[A,X] = A => X

  // type Yo f a = Functor f => Reader a -> f
  abstract class Yo[F[_]:Functor,A] {
    def apply(): Reader[A,*] ~> F[*]
  }

  type YonedaLemma[F[_],A] = Yo[F,A] ~ F[A]
  def yonedaLemma[F[_],A](implicit FF: Functor[F]): YonedaLemma[F,A] = new YonedaLemma[F,A]{
    def to: F[A] => Yo[F, A] = fa => new Yo[F,A] {
      def apply(): (A => *) ~> F = λ[(A => *) ~> F]( atox => FF.map(fa)(atox) )
    }
    def from: Yo[F, A] => F[A] =
      alpha => alpha()(identity[A])
  }

  type FX[B,X] = B => X
  type YonedaEmbedding[B,A] = YonedaLemma[B => *, A]
  type YonedaEmbedding2[B,A] = Yo[B => *,A] ~ B => A
  type YoEmbed1[A,B] = (Reader[A,*] ~> Reader[B,*]) ~ Reader[B,A]

  type YoEmbed[A,B] = (A => *) ~> (B => *) ~ (B => A)
  def yoEmbed[A,B]: (A => *) ~> (B => *) ~ (B=>A) =
    new ~[(A => *) ~> (B => *), B=>A] {
      def to: (B =>A) => (A => *) ~> (B => *) =
        ba => λ[(A => *) ~> (B => *)]( ax => ba andThen ax )
      def from: (A => *) ~> (B => *) => B=>A =
        alpha => alpha(identity[A])
    }

  trait ForAllF[G[_],H[_]]{
    def apply[F[_]](gf: G ~> F): (H ~> F)
  }

  type YoEmbedF[G[_],H[_]] = ForAllF[G,H] ~ (H ~> G)

  def yoEmbedF[G[_],H[_]]: YoEmbedF[G,H] = new YoEmbedF[G,H] {
    def to: H ~> G => ForAllF[G,H] = hg =>
      new ForAllF[G,H] { // TODO kind projector ?
        def apply[F[_]](gf: G ~> F): H ~> F = hg.andThen(gf)
      }
    def from: ForAllF[G,H] => H ~> G = gh =>
      gh(IdentityNat[G])
  }

  trait ToyOptic[S,A] { // Get
    def apply[F[_]](FF: Functor[F]): F[A] => F[S]
  }

  def ProReaderProfunctor[AA,BB]: Profunctor[ProReader[AA,BB,*,*]] = new Profunctor[ProReader[AA,BB,*,*]] {
    def dimap[S,T,A,B](sa: S=>A, bt: B=>T):
    ((A => AA, BB => B)) => (S => AA, BB => T) = { case(a,b) =>
      (sa andThen a, b andThen bt) }
  }
  abstract class ProYo[P[_,_]:Profunctor,A,B] {
    def apply(): ProReader[A,B,*,*] ~~> P[*,*]
  }

  type YonedaLemma4Pro[P[_,_],A,B] = ProYo[P,A,B] ~ P[A,B]

  trait ForAllP[R[_,_],Q[_,_]]{
    def apply[P[_,_]](gf: Q ~~> P): (R ~~> P)
  }

  type YoEmbedP[R[_,_],Q[_,_]] = ForAllP[R,Q] ~ (R ~~> Q)

  def IdentityDinat[P[_,_]]: P ~~> P =
    new ~~>[P,P] {
      def apply[A,B](fa: P[A,B]): P[A,B] = fa
    }

  def yoEmbedP[R[_,_],Q[_,_]]: YoEmbedP[R,Q] = new YoEmbedP[R,Q] {
    def to: R ~~> Q => ForAllP[R,Q] = rq => new ForAllP[R,Q]{
      def apply[P[_,_]](qp: Q ~~> P): R ~~> P = rq.andThen(qp)
    }
    def from: ForAllP[R, Q] => R ~~> Q = rq => rq(IdentityDinat)
  }
}
