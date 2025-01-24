package Triglav.cat2

import Triglav.cat2.IdNat.IdentityNat

object TwoCategoryFInstances {
  abstract final class Void[F] {
    def absurd[A[_]]: Void ~> A
  }

  trait NatCat2
      extends TwoCategory[~>] { // category of type constructors with one argument and natural transformations
    override def id[Obj[_]]: Obj ~> Obj = IdentityNat[Obj]
    override def compose[A[_], B[_], C[_]](f: B ~> C)(g: A ~> B): A ~> C =
      f compose g

    type Terminal[A] = Unit
    def unit[A[_]]: A ~> Terminal = λ[A ~> Terminal](_ => ())

    type Initial[A] = Void[A]
    def absurd[A[_]]: Initial ~> A = λ[Initial ~> A](v => v.absurd(v))
  }

  val scalaTypeConstructorsAndNaturalTransf: TwoCategory[~>] = new NatCat2 {}
}
