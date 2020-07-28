package Triglav.face2

// laws:
// dimap  (f andThen g) (h andThen i) ≡ dimap f h andThen bimap g i
// map (f compose g) ≡ map f compose map g -- from Joker
// contramap (f andThen g) ≡ contramap f andThen contramap g -- from Bard
trait Profunctor[:=:>[-_,+_]] extends Joker[:=:>] with Bard[:=:>] {
  def dimap[S,T,A,B](pab: A :=:> B)(sa: S => A, bt: B => T): S :=:> T

  // derived methods
  def lmap[S,A,B](pab: A :=:> B)(f: S => A): S :=:> B =
    dimap[S,B,A,B](pab)(f,identity[B])
  def rmap[A,B,T](pab: A :=:> B)(f: B => T): A :=:> T =
    dimap[A,T,A,B](pab)(identity[A],f)
}

