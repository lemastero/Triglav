package Triglav.face2

trait Joker[P[_,+_]] {
  def map[A,B,BB](fa: P[A,B])(g: B => BB): P[A,BB]
}

trait JokerLaws[P[_,+_]] extends Joker[P] {

  // map id == id
  def mapIdentity[A,B](p: P[A,B]): Boolean = {
    //          map(id)
    // P[A,B] ================> P[A,B]
    map(p)(identity[B]) == p
  }

  // map (f compose g) ≡ map f compose map g
  def mapComposition[A,B,B2,B3](pad: P[A,B], g: B => B2, f: B2 => B3): Boolean = {
    //          map B=>B2
    // P[A,B] ===================> F[A,B2]
    val p2: P[A,B2] = map(pad)(g)
    //          map B2=>B3
    // P[A,B2] ====================> P[A,B3]
    val l: P[A,B3] = map(p2)(f)

    val fg: B => B3 = f compose g
    //          map B=>B3
    // P[A,B] ======================> P[A,B3]
    val r: P[A,B3] = map(pad)(fg)

    l == r
  }
}
