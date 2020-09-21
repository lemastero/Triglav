package Triglav.face3

trait Trifunctor[F[-_,+_,+_]]
  extends Bard[F] with Clown[F] with Joker[F]
    with Biffunctor[F]
    with ClownProfunctor[F] with JokerProfunctor[F] {

  def timap[E,A,R,EE,AA,RR](fa: F[E,A,R])(
    f: EE => E,
    g: A => AA,
    h: R => RR): F[EE,AA,RR]

  // derived methods
  override def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R] =
    timap(fa)(f, identity[A], identity[R])

  override def mapLeft[E, A, R, AA](fa: F[E, A, R])(g: A => AA): F[E, AA, R] =
    timap(fa)(identity[E], g, identity[R])

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] =
    timap(fa)(identity[E], identity[A], h)

  override def bimap[E, A, R, AA, RR](fa: F[E, A, R])(g: A => AA, h: R => RR): F[E, AA, RR] =
    timap(fa)(identity[E], g, h)

  override def dimapLeft[E, A, R, EE, AA](fa: F[E, A, R])(f: EE => E, g: A => AA): F[EE, AA, R] =
    timap(fa)(f, g, identity[R])

  override def dimap[E, A, R, EE, RR](fa: F[E, A, R])(f: EE => E, h: R => RR): F[EE, A, RR] =
    timap(fa)(f, identity[A], h)
}

trait ProfunctorLaws[P[-_,+_,+_]]
  extends JokerLaws[P]
    with BardLaws[P]
    with ClownLaws[P]
    with Trifunctor[P] {

  // dimap id id == id
  def timapIdentity[A, B, C](p: P[A, B, C]): Boolean = {
    //          dimap(id, id, id)
    // P[A,B,C] ================> P[A,B,C]
    timap(p)(identity[A], identity[B], identity[C]) == p
  }

  // timap (f . g) (h . i) == dimap g h . dimap f i
  def timapComposition[A, B, C, A2, A3, B2, B3, C2, C3](pad: P[A, B, C], g: A3 => A2, f: A2 => A, i: B => B2, h: B2 => B3, k: C => C2, m: C2 => C3): Boolean = {
    //          timap A2=>A B=>B2 C=>C2
    // P[A,B] ==========================> F[A2,B2]
    val p2: P[A2, B2, C2] = timap(pad)(f, i, k)
    //          timap A3=>A2 B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: P[A3, B3, C3] = timap(p2)(g, h, m)

    val fg: A3 => A = f compose g
    val hi: B => B3 = h compose i
    val mk: C => C3 = m compose k
    //         timap A3=>A B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: P[A3, B3, C3] = timap(pad)(fg, hi, mk)

    l == r
  }

  // dimap f g == contramap (map g) f
  def timapCoherentWithMapAndContramap[A, B, C, A2, B2, C2](pad: P[A, B, C], f: A2 => A, g: B => B2, h: C => C2): Boolean = {
    //          dimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val l: P[A2, B2, C2] = timap(pad)(f, g, h)

    val r1 = map(pad)(h)
    val r2 = contramap(r1)(f)
    val r3 = mapLeft(r2)(g)

    l == r3
  }
}
