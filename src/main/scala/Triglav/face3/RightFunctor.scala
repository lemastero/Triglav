package Triglav.face3

trait RightFunctor[F[_, _, +_]] {
  def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR]
}

trait RightFunctorLaws[P[_, _, +_]] extends RightFunctor[P] {

  // map id == id
  def mapIdentity[A, B, C](p: P[A, B, C]): Boolean = {
    //          map(id)
    // P[A,B,C] ================> P[A,B,C]
    map[A, B, C, C](p)(identity[C]) == p
  }

  // map (f compose g) ≡ map f compose map g
  def mapComposition[A, B, C, C2, C3](
      pad: P[A, B, C],
      g: C => C2,
      f: C2 => C3
  ): Boolean = {
    //          map B=>B2
    // P[A,B] ===================> F[A,B2]
    val p2: P[A, B, C2] = map(pad)(g)
    //          map B2=>B3
    // P[A,B2] ====================> P[A,B3]
    val l: P[A, B, C3] = map(p2)(f)

    val fg: C => C3 = f compose g
    //          map B=>B3
    // P[A,B] ======================> P[A,B3]
    val r: P[A, B, C3] = map(pad)(fg)

    l == r
  }
}
