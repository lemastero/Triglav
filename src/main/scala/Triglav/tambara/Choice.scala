package Triglav.tambara

import Triglav.face2.Profunctor

trait Choice[=>:[-_,+_]] extends Profunctor[=>:] {
  def left[A,B,C]: (A =>: B) => (Either[A,C] =>: Either[B,C])      // TambaraModule Either(_,C)

  def right[A,B,C]: (A =>: B) => (Either[C,A] =>: Either[C,B]) = { // TambaraModule Either(C,_)
    val swap: Either[A, C] =>: Either[B, C] => Either[C, A] =>: Either[C, B] =
      pab => dimap[Either[C, A], Either[C, B], Either[A, C], Either[B, C]](pab)(_.swap, _.swap)
    swap compose left[A, B, C]
  }
}
