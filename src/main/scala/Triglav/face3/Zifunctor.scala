package Triglav.face3

trait Zifunctor[F[-_, +_, +_]]
    extends FirstContravariant[F]
    with LeftFunctor[F]
    with RightFunctor[F]
    with RightBifunctor[F]
    with LeftProfunctor[F]
    with RightProfunctor[F] {

  def zimap[E, A, R, EE, AA, RR](
      fa: F[E, A, R]
  )(f: EE => E, g: A => AA, h: R => RR): F[EE, AA, RR]

  // derived methods
  override def bimap[E, A, R, AA, RR](
      fa: F[E, A, R]
  )(g: A => AA, h: R => RR): F[E, AA, RR] =
    zimap(fa)(identity[E], g, h)

  override def dimapLeft[E, A, R, EE, AA](
      fa: F[E, A, R]
  )(f: EE => E, g: A => AA): F[EE, AA, R] =
    zimap(fa)(f, g, identity[R])

  override def dimap[E, A, R, EE, RR](
      fa: F[E, A, R]
  )(f: EE => E, h: R => RR): F[EE, A, RR] =
    zimap(fa)(f, identity[A], h)

  override def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R] =
    zimap(fa)(f, identity[A], identity[R])

  override def mapLeft[E, A, R, AA](fa: F[E, A, R])(g: A => AA): F[E, AA, R] =
    zimap(fa)(identity[E], g, identity[R])

  override def map[E, A, R, RR](fa: F[E, A, R])(h: R => RR): F[E, A, RR] =
    zimap(fa)(identity[E], identity[A], h)
}

trait ZifunctorLaws[P[-_, +_, +_]]
    extends RightFunctorLaws[P]
    with FirstContravariantLaws[P]
    with LeftFunctorLaws[P]
    with Zifunctor[P] {

  // dimap id id == id
  def zimapIdentity[A, B, C](p: P[A, B, C]): Boolean = {
    //          dimap(id, id, id)
    // P[A,B,C] ================> P[A,B,C]
    zimap(p)(identity[A], identity[B], identity[C]) == p
  }

  // zimap (f . g) (h . i) == dimap g h . dimap f i
  def zimapComposition[A, B, C, A2, A3, B2, B3, C2, C3](
      pad: P[A, B, C],
      g: A3 => A2,
      f: A2 => A,
      i: B => B2,
      h: B2 => B3,
      k: C => C2,
      m: C2 => C3
  ): Boolean = {
    //          zimap A2=>A B=>B2 C=>C2
    // P[A,B] ==========================> F[A2,B2]
    val p2: P[A2, B2, C2] = zimap(pad)(f, i, k)
    //          zimap A3=>A2 B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: P[A3, B3, C3] = zimap(p2)(g, h, m)

    val fg: A3 => A = f compose g
    val hi: B => B3 = h compose i
    val mk: C => C3 = m compose k
    //         zimap A3=>A B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: P[A3, B3, C3] = zimap(pad)(fg, hi, mk)

    l == r
  }

  // dimap f g == contramap (map g) f
  def zimapCoherentWithMapAndContramap[A, B, C, A2, B2, C2](
      pad: P[A, B, C],
      f: A2 => A,
      g: B => B2,
      h: C => C2
  ): Boolean = {
    //          dimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val l: P[A2, B2, C2] = zimap(pad)(f, g, h)

    val r1 = map(pad)(h)
    val r2 = contramap(r1)(f)
    val r3 = mapLeft(r2)(g)

    l == r3
  }
}
