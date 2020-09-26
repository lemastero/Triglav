package Triglav.face3

trait Clown[F[_,+_,_]] {
  def mapLeft[E,A,R,AA](fa: F[E,A,R])(g: A => AA): F[E,AA,R]
}

trait ClownLaws[P[_,+_,_]] extends Clown[P] {

  // mapLeft id == id
  def mapLeftIdentity[A,B,C](p: P[A,B,C]): Boolean = {
    //          mapLeft(id)
    // P[A,B,C] ================> P[A,B,C]
    mapLeft(p)(identity[B]) == p
  }

  // mapLeft (f compose g) ≡ mapLeft  f compose mapLeft  g
  def mapLeftComposition[A,B,C,B2,B3](p: P[A,B,C], g: B => B2, f: B2 => B3): Boolean = {
    //          mapLeft B=>B2
    // P[A,B] ===================> F[A2,B]
    val pbe: P[A,B2,C] = mapLeft(p)(g)
    //          mapLeft B2=>B3
    // P[A2,B] ====================> P[A3,B]
    val l: P[A,B3,C] = mapLeft(pbe)(f)

    val fg: B => B3 = f compose g
    //         mapLeft A=>A3
    // P[A,B] ===================> P[A3,B]
    val r: P[A,B3,C] = mapLeft(p)(fg)

    l == r
  }
}
