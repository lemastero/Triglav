package Triglav.monoidal1

import Triglav.monoidal1.MonoidalCategoryInstances.{Function1EitherMc, TupleMc}
import Triglav.face0.Void

trait BraidedMonoidalCategory[:=>[_,_],⊗[+_,+_], I]
  extends MonoidalCategory[:=>,⊗, I] {

  def braiding[A,B](a: A⊗B): B⊗A // swap
}

trait BraidedMonoidalCategoryLaws[:=>[_,_],⊗[+_,+_], I]
  extends BraidedMonoidalCategory[:=>,⊗, I]
    with MonoidalCategoryLaws[:=>,⊗, I] {

  def hexagonEquations[A,B,C,D](fa: ((A⊗B)⊗C)⊗D): Boolean = {
    //                      α[A,B,C] ⊗ 1D
    // ((A ⊗ B) ⊗ C) ⊗ D -----------------> (A ⊗ (B ⊗ C)) ⊗ D
    val v1: (A⊗(B⊗C))⊗D = tensor.bimap(fa)(α[A,B,C],identity[D])
    //                     α[A,B⊗C,D]
    // (A ⊗ (B ⊗ C)) ⊗ D ------------> A ⊗ ((B ⊗ C) ⊗ D)
    val v2: A⊗((B⊗C)⊗D) = α[A,B⊗C,D](v1)
    //                    1A ⊗ α[B,C,D]
    // A ⊗ ((B ⊗ C) ⊗ D) ------------------> A ⊗ (B ⊗ (C ⊗ D))
    val v3: A⊗(B⊗(C⊗D)) = tensor.bimap(v2)(identity[A],α[B,C,D])

    //                     α[A⊗B,C,D]
    // ((A ⊗ B) ⊗ C) ⊗ D -------------> (A ⊗ B) ⊗ (C ⊗ D)
    val w1: ((A⊗B)⊗(C⊗D)) = α[A⊗B,C,D](fa)
    //                     α[A,B,C⊗D]
    // (A ⊗ B) ⊗ (C ⊗ D) ------------> A ⊗ (B ⊗ (C ⊗ D))
    val w2: A⊗(B⊗(C⊗D)) = α[A,B,C⊗D](w1)

    v3 == w2
  }
}

object BraidedMonoidalCategoryInstances {
  val productBraidedMonoidalCategory: BraidedMonoidalCategory[Function1,Either,Void] =
    new BraidedMonoidalCategory[Function1,Either,Void]
      with Function1EitherMc {

      def braiding[A,B](a: Either[A,B]): Either[B,A] = a.swap
    }

  val tupleBraidedMonoidalCategory: BraidedMonoidalCategory[Function1,Tuple2,Unit] =
    new BraidedMonoidalCategory[Function1,Tuple2,Unit]
      with TupleMc {

      def braiding[A,B](a: (A,B)): (B,A) = a.swap
    }
}
