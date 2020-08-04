package Triglav.tambara

import Triglav.face2.{Profunctor, ProfunctorLaws}

trait CartesianStrong[P[-_,+_]] extends Profunctor[P] {   // TambaraModule (_,C)
  def first[A,B,C]:  (A P B) => P[(A,C),(B,C)]

  def second[A,B,C]: P[A,B] => P[(C,A),(C,B)] = {
    val doubleSwap: P[(A,C), (B,C)] => P[(C,A), (C,B)] =
      p => dimap[(C,A), (C,B), (A,C), (B,C)](p)(_.swap, _.swap)
    doubleSwap compose first[A,B,C]
  }
}

// see page 9 https://www.cs.ox.ac.uk/jeremy.gibbons/publications/proyo.pdf
trait CartesianStrongLaws[P[-_,+_]]
  extends CartesianStrong[P]
  with ProfunctorLaws[P] {

  // ((A,B), C) ~ (A, (B,C))
  private def assoc[A,B,C]: (((A,B), C)) => (A, (B,C))  = { case ((a,c),d) => (a,(c,d)) }
  private def unassoc[A,B,C]: ((A, (B,C))) => ((A,B), C) = { case (a,(c,d)) => ((a,c),d) }

  def secondSecondIsDimapReAssoc[A,B,C,D](p: P[A,B]): Boolean = {
    //          second[D]
    // P[A,B] ============> P[(D,A), (D,B)]
    val l1: P[(D,A), (D,B)] = second[A,B,D](p)
    //                  second[C]
    // P[(D,A), (D,B)] ==========> P[(C, (D,A)), (C, (D,B))]
    val l2: P[(C, (D,A)), (C, (D,B))] = second[(D,A),(D,B),C](l1)

    //          second[(C,D)]
    // P[A,B] ===============> P[((C,D),A), ((C,D),B)]
    val r1: P[((C,D), A), ((C,D), B)] = second[A,B,(C,D)](p)

    //                          dimap[(C,D)](unassoc, assoc)
    // P[((C,D),A), ((C,D),B)] =============================> P[((C,D),A), ((C,D),B)]
    val r2: P[(C,(D,A)), (C,(D,B))] = dimap[(C,(D,A)), (C,(D,B)),((C,D), A), ((C,D), B)](r1)(unassoc, assoc)

    l2 == r2
  }

  // ((),A) ~ A
  def lunit[A]: ((Unit,A)) => A = { case((), a) => a }
  def lunitr[A]: A => (Unit,A) = a => ((), a)

  def dimapLunitIsSecond[A,B,C,D](p: P[A,B]): Boolean = {
    //          second[D]
    // P[A,B] ============> P[(Unit,A), (Unit,B)]
    val l: P[(Unit,A), (Unit,B)] = second[A,B,Unit](p)

    //           dimap[(C,D)](lunit, lunitr)
    // P[A,B] =============================> P[(Unit,A), (Unit,B)]
    val r: P[(Unit,A), (Unit,B)] = dimap[(Unit,A), (Unit,B), A, B](p)(lunit, lunitr)

    l == r
  }

  // XXX add symmetric laws for first ?
}

object CartesianStrong {

  def uncurry[P[-_,+_],A,B,C](pa: P[A, B => C])(implicit S: CartesianStrong[P]): P[(A,B),C] = {
    S.map(S.first[A,B=>C,B](pa)){ case (bc, b) => bc(b) }
  }
}
