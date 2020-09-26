package Triglav.face3

// laws in each case are similar like in versions with 2 holes
trait Bard[F[-_, _, _]] {
  def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R]
}

trait BardLaws[P[-_, _, _]] extends Bard[P] {

  // contramap id == id
  def contramapIdentity[A, B, C](p: P[A, B, C]): Boolean = {
    //          contramap(id)
    // P[A,B] ================> P[A,B]
    contramap(p)(identity[A]) == p
  }

  // contramap (f . g) == contramap g . contramap f
  def contramapComposition[A, A2, A3, B, B2, B3, C](
      p: P[A, B, C],
      g: A3 => A2,
      f: A2 => A
  ): Boolean = {
    //          contramap B=>B2
    // P[A,B,C] ===================> F[A2,B2,C]
    val p2: P[A2, B, C] = contramap(p)(f)
    //          contramap B2=>B3
    // P[A2,B2,C] ====================> P[A3,B3,C]
    val l: P[A3, B, C] = contramap(p2)(g)

    val fg: A3 => A = f compose g
    //         contramap B=>B3
    // P[A,B,C] ===================> P[A3,B3,C]
    val r: P[A3, B, C] = contramap(p)(fg)

    l == r
  }
}
