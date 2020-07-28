package Triglav.face2

// law: contramap (f andThen g) ≡ contramap f andThen contramap g
trait Bard[:=:>[-_,_]] {
  def contramap[A,AA,B](fa: A :=:> B)(f: AA => A): AA :=:> B
}
