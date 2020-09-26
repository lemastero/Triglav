package Triglav.face2

trait Bard[:=:>[-_, _]] {
  def contramap[A, AA, B](fa: A :=:> B)(f: AA => A): AA :=:> B
}

trait BardLaws[P[-_, +_]] extends Bard[P] {

  // contramap id == id
  def contramapIdentity[A, B](p: P[A, B]): Boolean = {
    //          contramap(id)
    // P[A,B] ================> P[A,B]
    contramap(p)(identity[A]) == p
  }

  // contramap (f . g) == contramap g . contramap f
  def contramapComposition[A, A2, A3, B, B2, B3](
      p: P[A, B],
      g: A3 => A2,
      f: A2 => A
  ): Boolean = {
    //          contramap B=>B2
    // P[A,B] ===================> F[A2,B2]
    val p2: P[A2, B] = contramap(p)(f)
    //          contramap B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: P[A3, B] = contramap(p2)(g)

    val fg: A3 => A = f compose g
    //         contramap B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: P[A3, B] = contramap(p)(fg)

    l == r
  }
}
