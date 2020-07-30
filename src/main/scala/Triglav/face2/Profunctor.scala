package Triglav.face2

// laws:
// dimap  (f andThen g) (h andThen i) ≡ dimap f h andThen bimap g i
// map (f compose g) ≡ map f compose map g -- from Joker
// contramap (f andThen g) ≡ contramap f andThen contramap g -- from Bard
trait Profunctor[:=:>[-_,+_]] extends Joker[:=:>] with Bard[:=:>] {
  def dimap[S,T,A,B](pab: A :=:> B)(sa: S => A, bt: B => T): S :=:> T

  override def map[A,B,T](fa: A :=:> B)(f: B => T): A :=:> T =
    dimap(fa)(identity,f)
  override def contramap[A, AA, B](fa: A :=:> B)(f: AA => A): AA :=:> B =
    dimap(fa)(f,identity)
}

