package Triglav.eye

import Triglav.face2.Profunctor
import Triglav.tambara._

object Optics {
  type Optics[P[-_,+_],S,T,A,B] = P[A,B] => P[S,T]

  case class Adapter[S,T,A,B](from: S => A, to: B => T)
  trait AdapterP[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Profunctor[P]): Optics[P,S,T,A,B]
  }

  /*
  // adapterC2P:: Adapter a b s t -> AdapterP a b s t
  // adapterC2P (Adapter o i) = dimap o i
  def isoC2P[S,T,A,B]: Adapter[S,T,A,B] => AdapterP[S,T,A,B] = iso => new AdapterP[S,T,A,B] {
    override def apply[P[_,_]](implicit PP: Profunctor[P]): Optics[P,S,T,A,B] =
      (pab: P[A,B]) => PP.dimap(pab)(iso.from, iso.to)
  }

  // Profunctor Adapter
  def AdapterProfunctor[A,B] = new Profunctor[Adapter[*,*,A,B]] {
    def dimap[S,T,SX,TX](f: S=>SX, g: TX=>T): Adapter[SX,TX,A,B] => Adapter[S,T,A,B] = {
      case Adapter(from,to) => Adapter(f andThen from, to andThen g)
    }
  }

  // adapterP2C :: AdapterP a b s t -> Adapter a b s t
  // adapterP2C l = l (Adapter id id)
  def adapterP2C[S,T,A,B]: AdapterP[S,T,A,B] => Adapter[S,T,A,B] = l => {
    def isoOptics: Optics[Adapter[*,*,A,B], S,T,A,B] = l(AdapterProfunctor[A,B]) // Optics[P[_,_],S,T,A,B] = P[A,B] => P[S,T]
    isoOptics(Adapter[A,B,A,B](identity[A],identity[B]))
  }
  */

  trait Lens[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Strong[P]): Optics[P,S,T,A,B]
  }

  trait Prism[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Choice[P]): Optics[P,S,T,A,B]
  }

  trait Grate[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Closed[P]): Optics[P,S,T,A,B]
  }

  trait Glass[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Glassed[P]): Optics[P,S,T,A,B]
  }

  trait AffineTraversal[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Magnified[P]): Optics[P,S,T,A,B]
  }

  trait AffineWindow[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Telescoped[P]): Optics[P,S,T,A,B]
  }

  trait Window[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Windowed[P]): Optics[P,S,T,A,B]
  }

  trait Traversal[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Traversing[P]): Optics[P,S,T,A,B]
  }

  trait Grid[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Polynodal[P]): Optics[P,S,T,A,B]
  }

  trait Kaleidescope[S,T,A,B] {
    def apply[P[-_,+_]](implicit PP: Polyp[P]): Optics[P,S,T,A,B]
  }
}
