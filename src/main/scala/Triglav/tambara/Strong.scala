package Triglav.tambara

import Triglav.face2.Profunctor

trait Strong[=>:[-_,+_]] extends Profunctor[=>:] {   // TambaraModule (_,C)
  def first[A,B,C]:  (A =>: B) => ((A,C) =>: (B,C))

  def second[A,B,C]: (A =>: B) => ((C,A) =>: (C,B)) = {
    val doubleSwap: (A, C) =>: (B, C) => (C, A) =>: (C, B) =
      p => dimap[(C, A), (C, B), (A, C), (B, C)](p)(_.swap, _.swap)
    doubleSwap compose first[A,B,C]
  }
}

object Strong {
  type Arrow[=>:[-_,+_]] = Strong[=>:]

  def uncurry[P[-_,+_],A,B,C](pa: P[A, B => C])(implicit S: Strong[P]): P[(A,B),C] = {
    S.map(S.first[A,B=>C,B](pa)){ case (bc, b) => bc(b) }
  }
}
