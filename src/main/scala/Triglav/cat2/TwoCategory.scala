package Triglav.cat2

import Triglav.cat2.IdNat.IdentityNat

trait Semi2Category[OneCell[_[_],_[_]]] {
  def compose[ObA[_],ObB[_],ObC[_]](f: OneCell[ObB,ObC])(g: OneCell[ObA,ObB]): OneCell[ObA,ObC]
  // TODO TwoCell
}

trait Semi2CategoryLaws[M[_[_],_[_]]] extends Semi2Category[M] {

  def compositivityLaw[A[_],B[_],C[_],D[_]](g: M[B,C], f: M[A,B], h: M[C,D]): Boolean = {
    val gf: M[A, C] = compose(g)(f)
    val v2: M[A, D] = compose(h)(gf)

    val hg: M[B, D] = compose(h)(g)
    val w2: M[A, D] = compose(hg)(f)

    v2 == w2
  }
}

trait TwoCategory[Morphism[_[_],_[_]]] extends Semi2Category[Morphism] {
  def id[Obj[_]]: Morphism[Obj,Obj]
}

trait TwoCategoryLaws[M[_[_],_[_]]] extends TwoCategory[M] {
  def leftIdentityLaw[A[_],B[_]](fa: M[A,B]): Boolean = {
    compose(id[B])(fa) == fa
  }

  def rightIdentityLaw[A[_],B[_]](fa: M[A,B]): Boolean = {
    compose(fa)(id[A]) == fa
  }
}

object TwoCategoryInstances {
  abstract final class Void[F] {
    def absurd[A[_]]: Void ~> A
  }

  trait NatCat2 extends TwoCategory[~>] { // category of type constructors with one argument and natural transformations
    override def id[Obj[_]]: Obj ~> Obj = IdentityNat[Obj]
    override def compose[A[_], B[_], C[_]](f: B ~> C)(g: A ~> B): A ~> C = f compose g

    type Terminal[A] = Unit
    def unit[A[_]]: A ~> Terminal = λ[A ~> Terminal]( _ => () )

    type Initial[A] = Void[A]
    def absurd[A[_]]: Initial ~> A = λ[Initial ~> A]( v => v.absurd(v) )
  }

  val scalaTypeConstructorsAndNaturalTransf: TwoCategory[~>] = new NatCat2 {}
}
