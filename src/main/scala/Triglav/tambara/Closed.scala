package Triglav.tambara

import Triglav.face2.{Profunctor, ProfunctorLaws}

trait Closed[=>:[-_, +_]] extends Profunctor[=>:] { // TambaraModule C => _
  def closed[A, B, C]: A =>: B => (C => A) =>: (C => B)
}

trait ClosedLaws[P[-_, +_]] extends Closed[P] with ProfunctorLaws[P] {

  // TODO

//  // (A => B) => C  ~  A => (B => C)
//  private def assoc[A,B,C]: ((A => B) => C) => (A => (B => C)) = (ab_c: ((A => B) => C) ) => {
//    (_: A) => {
//      (b: B) => {
//        def ab: A => B = _ => b
//        ab_c(ab)
//      }
//    }
//  } // this is cheating !
//
//  private def unassoc[A,B,C]: (A => (B => C)) => ((A => B) => C) = ???
//
//  def secondSecondIsDimapReAssoc[A,B,C,D](p: P[A,B]): Boolean = {
//    //          closed[D]
//    // P[A,B] ============> P[D => A, D => B]
//    val l1: P[D => A, D => B] = closed[A,B,D](p)
//    //                    closed[C]
//    // P[D => A, D => B] ==========> P[C => (D => A), C => (D => B)]
//    val l2: P[C => (D => A), C => (D => B)] = closed[D => A, D => B, C](l1)
//
//    //          second[(C,D)]
//    // P[A,B] ===============> P[((C,D),A), ((C,D),B)]
//    val r1: P[(C => D) => A, (C => D) => B] = closed[A,B,C => D](p)
//
//    //                          dimap[(C,D)](unassoc, assoc)
//    // P[((C,D),A), ((C,D),B)] =============================> P[C => (D => A), C => (D => B)]
//    val r2: P[C => (D => A), C => (D => B)] = dimap[C => (D => A), C => (D => B), (C => D) => A, (C => D) => B](r1)(unassoc, assoc)
//
//    l2 == r2
//  }
//
//  // ((),A) ~ A
//  def lunit[A]: ((Unit,A)) => A = { case((), a) => a }
//  def lunitr[A]: A => (Unit,A) = a => ((), a)

//  def dimapLunitIsSecond[A,B,C,D](p: P[A,B]): Boolean = {
//    //          second[D]
//    // P[A,B] ============> P[(Unit,A), (Unit,B)]
//    val l: P[(Unit,A), (Unit,B)] = second[A,B,Unit](p)
//
//    //           dimap[(C,D)](lunit, lunitr)
//    // P[A,B] =============================> P[(Unit,A), (Unit,B)]
//    val r: P[(Unit,A), (Unit,B)] = dimap[(Unit,A), (Unit,B), A, B](p)(lunit, lunitr)
//
//    l == r
//  }
}
