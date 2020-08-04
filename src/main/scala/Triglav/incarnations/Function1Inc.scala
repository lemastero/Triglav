package Triglav.incarnations

import Triglav.face2.Profunctor
import Triglav.tambara.CartesianStrong

object Function1Inc {

  def function1Profunctor: Profunctor[Function1] = new CartesianStrong[Function1] {

    // profunctor
    override def dimap[S,T,A,B](ab: A => B)(sa: S => A, bt: B => T): S => T =
      sa andThen ab andThen bt

    // strong
    override def first[A, B, C]: Function[A, B] => Function[(A, C), (B, C)] = f => { case (x,z) => (f(x), z) }
  }
}
