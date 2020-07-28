package Triglav.face2

// laws:
// bimap  (f compose g) (h compose i) ≡ bimap f h compose bimap g i
// mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g - inherited from Clown
// map (f compose g) ≡ map f compose map g - inherited from Joker
trait Bifunctor[:=:>[+_,+_]] extends Joker[:=:>] with Clown[:=:>] {

  def bimap[AA,A,B,BB](fa: A :=:> B)(f: A => AA, g: B => BB): AA :=:> BB = {
    val v1: A :=:> BB = map(fa)(g)
    mapLeft(v1)(f)
  }
}
