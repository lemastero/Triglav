package Triglav.monoidal1

import Triglav.cat1.Category
import Triglav.face2.{Bifunctor, BifunctorInstances}
import Triglav.cat1.Cat1Instances.Function1Cat
import Triglav.cat1.Cat1Instances.TyFun1Cat.Initial.Initial

/**
  * Monoidal Categories based on Category of Scala types and functions
  *
  * Edward Kmett Discrimination is Wrong: Improving Productivity
  * http://yowconference.com.au/slides/yowlambdajam2015/Kmett-DiscriminationIsWrong.pdf
  */
trait MonoidalCategory[:=>[_, _], ⊗[+_, +_], I] extends Category[:=>] {

  def tensor: Bifunctor[⊗]
  def ρ[A](fa: A ⊗ I): A // right unitor
  def ρ_inv[A](a: A): A ⊗ I

  def λ[A](fa: I ⊗ A): A // left unitor
  def λ_inv[A, B](a: A): I ⊗ A

  def α[A, B, C](fa: (A ⊗ B) ⊗ C): A ⊗ (B ⊗ C) // associator
  def α_inv[A, B, C](fa: A ⊗ (B ⊗ C)): (A ⊗ B) ⊗ C
}

trait MonoidalCategoryLaws[:=>[_, _], ⊗[+_, +_], I]
    extends MonoidalCategory[:=>, ⊗, I] {

  def triangleEquations[A, B](fa: (A ⊗ I) ⊗ B): Boolean = {
    //               ρ[A] ⊗ id[B]
    // (A ⊗ I) ⊗ B ----------------> A ⊗ B
    val v1: A ⊗ B = tensor.bimap(fa)(ρ[A], identity[B])

    //              α[A,I,B]
    // (A ⊗ I) ⊗ B ---------->  A ⊗ (I ⊗ B)
    val w1: A ⊗ (I ⊗ B) = α[A, I, B](fa)

    //               id[A] ⊗ λ[B]
    // A ⊗ (I ⊗ B) ---------------> A ⊗ B
    val w2: A ⊗ B = tensor.bimap(w1)(identity[A], λ[B])

    v1 == w2
  }

  def pentagonEquations[A, B, C, D](fa: ((A ⊗ B) ⊗ C) ⊗ D): Boolean = {
    //                    α[A,B,C] ⊗ 1D
    // ((A ⊗ B) ⊗ C) ⊗ D ---------------> (A ⊗ (B ⊗ C)) ⊗ D
    val v1: (A ⊗ (B ⊗ C)) ⊗ D = tensor.bimap(fa)(α[A, B, C], identity[D])

    //                    α[A,B⊗C,D]
    // (A ⊗ (B ⊗ C)) ⊗ D ------------> A ⊗ ((B ⊗ C) ⊗ D)
    val v2: A ⊗ ((B ⊗ C) ⊗ D) = α[A, B ⊗ C, D](v1)

    //                    1A ⊗ α[B,C,D]
    // A ⊗ ((B ⊗ C) ⊗ D) ---------------> A ⊗ (B ⊗ (C ⊗ D))
    val v3: A ⊗ (B ⊗ (C ⊗ D)) = tensor.bimap(v2)(identity[A], α[B, C, D])

    //                     α[A⊗B,C,D]
    // ((A ⊗ B) ⊗ C) ⊗ D -------------> (A ⊗ B) ⊗ (C ⊗ D)
    val w1: (A ⊗ B) ⊗ (C ⊗ D) = α[A ⊗ B, C, D](fa)

    //                      α[A,B,C⊗D]
    // (A ⊗ B) ⊗ (C ⊗ D) -------------> A ⊗ (B ⊗ (C ⊗ D))
    val w2: A ⊗ (B ⊗ (C ⊗ D)) = α[A, B, C ⊗ D](w1)

    v3 == w2
  }
}

object MonoidalCategoryInstances {

  trait TupleMc
      extends MonoidalCategory[Function1, Tuple2, Unit]
      with Function1Cat {

    val tensor: Bifunctor[Tuple2] = BifunctorInstances.tupleBifunctor
    def ρ[A](fa: (A, Unit)): A = fa._1
    def ρ_inv[A](a: A): (A, Unit) = (a, ())
    def λ[A](fa: (Unit, A)): A = fa._2
    def λ_inv[A, B](a: A): (Unit, A) = ((), a)
    def α[A, B, C](fa: ((A, B), C)): (A, (B, C)) = fa match {
      case ((a, b), c) => (a, (b, c))
    }
    def α_inv[A, B, C](fa: (A, (B, C))): ((A, B), C) = fa match {
      case (a, (b, c)) => ((a, b), c)
    }
  }

  val productMCat: MonoidalCategory[Function1, Tuple2, Unit] = new TupleMc {}

  trait Function1EitherMc
      extends MonoidalCategory[Function1, Either, Initial]
      with Function1Cat {

    val tensor: Bifunctor[Either] = BifunctorInstances.eitherBifunctor
    def ρ[A](fa: Either[A, Initial]): A = fa match {
      case Left(a)  => a
      case Right(v) => v.absurd[A]
    }
    def ρ_inv[A](a: A): Either[A, Initial] = Left(a)
    def λ[A](fa: Either[Initial, A]): A = fa match {
      case Right(a) => a
      case Left(a)  => a.absurd[A]
    }
    def λ_inv[A, B](a: A): Either[Initial, A] = Right(a)
    def α[A, B, C](fa: Either[Either[A, B], C]): Either[A, Either[B, C]] =
      fa match {
        case Left(Left(a))  => Left(a)
        case Left(Right(b)) => Right(Left(b))
        case Right(c)       => Right(Right(c))
      }
    def α_inv[A, B, C](fa: Either[A, Either[B, C]]): Either[Either[A, B], C] =
      fa match {
        case Left(a)         => Left(Left(a))
        case Right(Left(b))  => Left(Right(b))
        case Right(Right(c)) => Right(c)
      }
  }

  val coproductMonCat: MonoidalCategory[Function1, Either, Initial] =
    new Function1EitherMc {}
}
