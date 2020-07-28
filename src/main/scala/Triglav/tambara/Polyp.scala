package Triglav.tambara

import Triglav.face1.Applicative
import Triglav.face2.Profunctor

trait Polyp[=>:[-_,+_]] extends Profunctor[=>:] {
  def polyper[A,B,F[_]](implicit A: Applicative[F]): A =>: B => F[A] =>: F[B]
}
