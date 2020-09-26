package Triglav.face2

trait Clown[P[+_, _]] {
  def mapLeft[A, AA, B](fa: P[A, B])(g: A => AA): P[AA, B]
}

trait ClownLaws[P[+_, _]] extends Clown[P] {

  // mapLeft id == id
  def mapLeftIdentity[A, B](p: P[A, B]): Boolean = {
    //          mapLeft(id)
    // P[A,B] ================> P[A,B]
    mapLeft(p)(identity[A]) == p
  }

  // mapLeft (f compose g) ≡ mapLeft  f compose mapLeft  g
  def mapLeftComposition[A, A2, B, A3](
      p: P[A, B],
      g: A => A2,
      f: A2 => A3
  ): Boolean = {
    //          mapLeft A=>A2
    // P[A,B] ===================> F[A2,B]
    val pbe: P[A2, B] = mapLeft(p)(g)
    //          mapLeft A2=>A3
    // P[A2,B] ====================> P[A3,B]
    val l: P[A3, B] = mapLeft(pbe)(f)

    val fg: A => A3 = f compose g
    //         mapLeft A=>A3
    // P[A,B] ===================> P[A3,B]
    val r: P[A3, B] = mapLeft(p)(fg)

    l == r
  }
}
