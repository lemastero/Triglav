package Triglav.face3

trait Trifunctor[T[+_, +_, +_]]
    extends FirstFunctor[T]
    with LeftFunctor[T]
    with RightFunctor[T]
    with LeftBifunctor[T]
    with RightBifunctor[T] {

  def trimap[E, A, R, EE, AA, RR](
      fa: T[E, A, R]
  )(f: E => EE, g: A => AA, h: R => RR): T[EE, AA, RR]

  // derived methods

  override def bimap[E, A, R, AA, RR](
      fa: T[E, A, R]
  )(g: A => AA, h: R => RR): T[E, AA, RR] =
    trimap(fa)(identity[E], g, h)

  override def bimapLeft[E, A, R, EE, AA](
      fa: T[E, A, R]
  )(f: E => EE, g: A => AA): T[EE, AA, R] =
    trimap(fa)(f, g, identity[R])

  override def mapFirst[E, A, R, EE](fa: T[E, A, R])(f: E => EE): T[EE, A, R] =
    trimap(fa)(f, identity[A], identity[R])

  override def mapLeft[E, A, R, AA](fa: T[E, A, R])(g: A => AA): T[E, AA, R] =
    trimap(fa)(identity[E], g, identity[R])

  override def map[E, A, R, RR](fa: T[E, A, R])(h: R => RR): T[E, A, RR] =
    trimap(fa)(identity[E], identity[A], h)
}

trait TrifunctorLaws[T[+_, +_, +_]]
    extends FirstFunctorLaws[T]
    with LeftFunctorLaws[T]
    with RightFunctorLaws[T]
    with LeftBifunctorLaws[T]
    with RightBifunctorLaws[T]
    with Trifunctor[T] {

  // trimap id id id == id
  def trimapIdentity[A, B, C](p: T[A, B, C]): Boolean = {
    //          trimap(id, id, id)
    // P[A,B,C] ================> P[A,B,C]
    trimap(p)(identity[A], identity[B], identity[C]) == p
  }

  // trimap f, i, k andThen trimap (g, h, m) == trimap g . f , h . i, m . k
  def trimapComposition[A, B, C, A2, A3, B2, B3, C2, C3](
      pad: T[A, B, C],
      f: A => A2,
      g: A2 => A3,
      i: B => B2,
      h: B2 => B3,
      k: C => C2,
      m: C2 => C3
  ): Boolean = {
    //          trimap A=>A2 B=>B2 C=>C2
    // P[A,B,C] ==========================> F[A2,B2,C2]
    val p2: T[A2, B2, C2] = trimap(pad)(f, i, k)
    //          trimap A3=>A2 B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: T[A3, B3, C3] = trimap(p2)(g, h, m)

    val gf: A => A3 = g compose f
    val hi: B => B3 = h compose i
    val mk: C => C3 = m compose k
    //         trimap A3=>A B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: T[A3, B3, C3] = trimap(pad)(gf, hi, mk)

    l == r
  }

  // trimap f g == (map h) andThen (mapFirst f) andThen (mapLeft g)
  def trimapCoherentWithMapAndContramap[A, B, C, A2, B2, C2](
      pad: T[A, B, C],
      f: A => A2,
      g: B => B2,
      h: C => C2
  ): Boolean = {
    //          trimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val l: T[A2, B2, C2] = trimap(pad)(f, g, h)

    val r1 = map(pad)(h)
    val r2 = mapFirst(r1)(f)
    val r3 = mapLeft(r2)(g)

    l == r3
  }
}
