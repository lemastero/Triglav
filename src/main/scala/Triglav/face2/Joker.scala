package Triglav.face2

// law: map (f compose g) ≡ map f compose map g
trait Joker[:=:>[_,+_]] {
  def map[A,B,BB](fa: A :=:> B)(g: B => BB): A :=:> BB
}
