package Triglav.optics

import Triglav.optics.classic.GeneralizedOpics.Adapter
import Triglav.face2.Profunctor
import Triglav.tambara._

object ProfunctorOptics {
  type Optics[P[-_,+_],-S,+T,+A,-B] = P[A,B] => P[S,T]

  trait AdapterP[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Profunctor[P]): Optics[P,S,T,A,B]
  }

  // adapterC2P:: Adapter a b s t -> AdapterP a b s t
  // adapterC2P (Adapter o i) = dimap o i
  def adapterC2P[S,T,A,B]: Adapter[S,T,A,B] => AdapterP[S,T,A,B] = iso => new AdapterP[S,T,A,B] {
    override def apply[P[-_,+_]](implicit PP: Profunctor[P]): Optics[P,S,T,A,B] =
      (pab: P[A,B]) => PP.dimap(pab)(iso.from, iso.to)
  }

  // Profunctor Adapter
  def AdapterProfunctor[X,Y] = {
    type ProfAdapter[-S,+T] = Adapter[S,T,X,Y]
    new Profunctor[ProfAdapter] {
      override def dimap[S,T,A,B](
        pab: Adapter[A,B,X,Y])(
        sa: S => A,
        bt: B => T): Adapter[S,T,X,Y] =
          Adapter(sa andThen pab.from, pab.to andThen bt)
    }
  }

  // adapterP2C :: AdapterP a b s t -> Adapter a b s t
  // adapterP2C l = l (Adapter id id)
//  def adapterP2C[S,T,A,B]: AdapterP[S,T,A,B] => Adapter[S,T,A,B] = l => {
//    def isoOptics: Optics[Adapter[*,*,A,B], S,T,A,B] = l(AdapterProfunctor[A,B]) // Optics[P[_,_],S,T,A,B] = P[A,B] => P[S,T]
//    isoOptics(Adapter[A,B,A,B](identity[A],identity[B]))
//  }

  trait Lens[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: CartesianStrong[P]): Optics[P,S,T,A,B]
  }

  trait Prism[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: CoCartesianChoice[P]): Optics[P,S,T,A,B]
  }

  trait Grate[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Closed[P]): Optics[P,S,T,A,B]
  }

  trait Glass[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Glassed[P]): Optics[P,S,T,A,B]
  }

  trait AffineTraversal[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Magnified[P]): Optics[P,S,T,A,B]
  }

  trait AffineWindow[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Telescoped[P]): Optics[P,S,T,A,B]
  }

  trait Window[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Windowed[P]): Optics[P,S,T,A,B]
  }

  trait Traversal[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Traversing[P]): Optics[P,S,T,A,B]
  }

  trait Grid[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Polynodal[P]): Optics[P,S,T,A,B]
  }

  trait Kaleidescope[-S,+T,+A,-B] {
    def apply[P[-_,+_]](implicit PP: Polyp[P]): Optics[P,S,T,A,B]
  }
}
