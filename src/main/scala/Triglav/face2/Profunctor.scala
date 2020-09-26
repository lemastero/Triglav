package Triglav.face2

trait Profunctor[P[-_, +_]] extends Joker[P] with Bard[P] {
  def dimap[S, T, A, B](pab: P[A, B])(sa: S => A, bt: B => T): P[S, T]

  override def map[A, B, T](fa: P[A, B])(f: B => T): P[A, T] =
    dimap(fa)(identity, f)
  override def contramap[A, AA, B](fa: P[A, B])(f: AA => A): P[AA, B] =
    dimap(fa)(f, identity)
}

trait ProfunctorLaws[P[-_, +_]]
    extends JokerLaws[P]
    with BardLaws[P]
    with Profunctor[P] {

  // dimap id id == id
  def dimapIdentity[A, B](p: P[A, B]): Boolean = {
    //          dimap(id, id)
    // P[A,B] ================> P[A,B]
    dimap(p)(identity[A], identity[B]) == p
  }

  // dimap (f . g) (h . i) == dimap g h . dimap f i
  def dimapComposition[A, A2, A3, B, B2, B3](
      pad: P[A, B],
      g: A3 => A2,
      f: A2 => A,
      i: B => B2,
      h: B2 => B3
  ): Boolean = {
    //          dimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val p2: P[A2, B2] = dimap(pad)(f, i)
    //          dimap A3=>A2 B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: P[A3, B3] = dimap(p2)(g, h)

    val fg: A3 => A = f compose g
    val hi: B => B3 = h compose i
    //         dimap A3=>A B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: P[A3, B3] = dimap(pad)(fg, hi)

    l == r
  }

  // dimap f g == contramap (map g) f
  def dimapCoherentWithMapAndContramap[A, A2, A3, B, B2, B3](
      pad: P[A, B],
      f: A2 => A,
      g: B => B2
  ): Boolean = {
    //          dimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val l: P[A2, B2] = dimap(pad)(f, g)

    val r = contramap(map(pad)(g))(f)

    l == r
  }
}
