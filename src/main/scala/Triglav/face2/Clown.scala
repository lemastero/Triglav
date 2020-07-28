package Triglav.face2

// law: mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g
trait Clown[:=:>[+_,_]] {
  def mapLeft[A,AA,B](fa: A :=:> B)(g: A => AA): AA :=:> B
}
