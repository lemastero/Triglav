package Triglav.tambara

import Triglav.face2.{Profunctor, ProfunctorLaws}
import Triglav.instances.EitherInc.\/
import Triglav.cat1.Cat1Instances.TyFun1Cat.Initial.Initial
import Triglav.monoidal1.MonoidalCategoryInstances.coproductMonCat.{
  α,
  α_inv,
  λ,
  λ_inv
}

trait CoCartesianChoice[=>:[-_, +_]] extends Profunctor[=>:] {
  def left[A, B, C]: (
      A =>: B
  ) => (Either[A, C] =>: Either[B, C]) // TambaraModule Either(_,C)

  def right[A, B, C]: (A =>: B) => (Either[C, A] =>: Either[C, B]) = { // TambaraModule Either(C,_)
    val swap: Either[A, C] =>: Either[B, C] => Either[C, A] =>: Either[C, B] =
      pab =>
        dimap[Either[C, A], Either[C, B], Either[A, C], Either[B, C]](pab)(
          _.swap,
          _.swap
        )
    swap compose left[A, B, C]
  }
}

trait CoCartesianChoiceLaws[P[-_, +_]]
    extends CoCartesianChoice[P]
    with ProfunctorLaws[P] {

  // ((A \/ B) \/ C) ~ (A \/ (B \/ C))
//  private def coassoc[A,B,C]: (A \/ B) \/ C => A \/ (B \/ C) = α
//  private def uncoassoc[A,B,C]: A \/ (B \/ C) => (A \/ B) \/ C = α_inv

  def rightRightIsDimapReUnAssoc[A, B, C, D](p: P[A, B]): Boolean = {
    //          second[D]
    // P[A,B] ============> P[D \/ A, D \/ B]
    val l1: P[D \/ A, D \/ B] = right[A, B, D](p)
    //                  second[C]
    // P[(D,A), (D,B)] ==========> P[(C, (D,A)), (C, (D,B))]
    val l2: P[C \/ (D \/ A), C \/ (D \/ B)] = right[D \/ A, D \/ B, C](l1)

    //          second[(C,D)]
    // P[A,B] ===============> P[(C \/ D) \/ A, (C \/ D) \/ B]
    val r1: P[(C \/ D) \/ A, (C \/ D) \/ B] = right[A, B, C \/ D](p)

    //                          dimap[(C,D)](unassoc, coassoc)
    // P[((C,D),A), ((C,D),B)] =============================> P[((C,D),A), ((C,D),B)]
    val r2: P[C \/ (D \/ A), C \/ (D \/ B)] =
      dimap[C \/ (D \/ A), C \/ (D \/ B), (C \/ D) \/ A, (C \/ D) \/ B](r1)(
        α_inv,
        α
      )

    l2 == r2
  }

  // (Nothing \/ A)  ~ A
//  def lzero[A]: (Void \/ A) => A = λ
//  def lzeror[A]: A => (Void \/ A) = λ_inv

  def dimapLunitIsSecond[A, B, C, D](p: P[A, B]): Boolean = {
    //          right[D]
    // P[A,B] ============> P[0 + A, 0 + B]
    val l: P[Initial \/ A, Initial \/ B] = right[A, B, Initial](p)

    //           dimap(lzero, lzeror)
    // P[A,B] =============================> P[0 + A, 0 + B]
    val r: P[Initial \/ A, Initial \/ B] =
      dimap[Initial \/ A, Initial \/ B, A, B](p)(λ, λ_inv)

    l == r
  }

  // XXX add symmetric laws for left ?
}
