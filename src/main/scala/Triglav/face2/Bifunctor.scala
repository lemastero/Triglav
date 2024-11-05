package Triglav.face2

// laws:
// bimap  (f compose g) (h compose i) ≡ bimap f h compose bimap g i
// mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g - inherited from Clown
// map (f compose g) ≡ map f compose map g - inherited from Joker
trait Bifunctor[:=:>[+_, +_]] extends Joker[:=:>] with Clown[:=:>] {

  def bimap[AA, A, B, BB](fa: A :=:> B)(f: A => AA, g: B => BB): AA :=:> BB

  override def map[A, B, BB](fa: A :=:> B)(g: B => BB): A :=:> BB =
    bimap(fa)(identity[A], g)
  override def mapLeft[A, AA, B](fa: A :=:> B)(f: A => AA): AA :=:> B =
    bimap(fa)(f, identity[B])
}

trait BifunctorLaws[P[+_, +_]]
    extends JokerLaws[P]
    with ClownLaws[P]
    with Bifunctor[P] {

  // dimap id id == id
  def bimapIdentity[A, B](p: P[A, B]): Boolean = {
    //          bimap(id, id)
    // P[A,B] ================> P[A,B]
    bimap(p)(identity[A], identity[B]) == p
  }

  // bimap (g . f) (h . i) == bimap g h . bimap f i
  def bimapComposition[A, A2, A3, B, B2, B3](
      pad: P[A, B],
      g: A2 => A3,
      f: A => A2,
      i: B => B2,
      h: B2 => B3
  ): Boolean = {
    //          bimap A=>A2 B=>B2
    // P[A,B] ===================> F[A2,B2]
    val p2: P[A2, B2] = bimap(pad)(f, i)
    //          dimap A2=>A3 B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: P[A3, B3] = bimap(p2)(g, h)

    val fg: A => A3 = g compose f
    val hi: B => B3 = h compose i
    //         bimap A3=>A B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: P[A3, B3] = bimap(pad)(fg, hi)

    l == r
  }

  // dimap f g == contramap (map g) f
  def bimapCoherentWithMapAndLeftamap[A, A2, A3, B, B2, B3](
      pad: P[A, B],
      f: A => A2,
      g: B => B2
  ): Boolean = {
    //          bimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val l: P[A2, B2] = bimap(pad)(f, g)

    val r = mapLeft(map(pad)(g))(f)

    l == r
  }
}

object BifunctorInstances {

  implicit val tupleBifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {

    override def bimap[AA, A, B, BB](
        fa: (A, B)
    )(f: A => AA, g: B => BB): (AA, BB) =
      fa match { case (a, c) => (f(a), g(c)) }
  }

  implicit val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
    def bimap[AA, A, B, BB](
        fa: Either[A, B]
    )(f: A => AA, g: B => BB): Either[AA, BB] = fa match {
      case Left(a)  => Left(f(a))
      case Right(c) => Right(g(c))
    }
  }

//  implicit def tuple3BifunctorXab[X]: Bifunctor[Tuple3[X,*,*]] = new Bifunctor[(X,*,*)] {
//    def bimap[AA,A,B,BB](fa: (X,A,B))(f: A => AA, g: B => BB): (X,AA,BB) =
//    { fa match { case (x,a,b) => (x, f(a), g(b)) } }
//  }
//
//  implicit def tuple3BifunctoraXb[X]: Bifunctor[(*,X,*)] = new Bifunctor[(*,X,*)] {
//    def bimap[AA,A,B,BB](fa: (A,X,B))(f: A => AA, g: B => BB): (AA,X,BB) =
//    { fa match { case (a,x,b) => (f(a), x, g(b)) } }
//  }
//
//  implicit def tuple3BifunctorabX[X]: Bifunctor[(*,*,X)] = new Bifunctor[(*,*,X)] {
//    def bimap[AA,A,B,BB](fa: (A,B,X))(f: A => AA, g: B => BB): (AA,BB,X) =
//    { fa match { case (a,b,x) => (f(a), g(b), x) } }
//  }
//
//  implicit def tuple4Bifunctor[X1,X2]: Bifunctor[(X1,X2,*,*)] = new Bifunctor[(X1,X2,*,*)] {
//    def bimap[AA,A,B,BB](fa: (X1,X2,A,B))(f: A => AA, g: B => BB): (X1,X2,AA,BB) =
//      fa match { case (a,b,c,d) => (a, b, f(c), g(d)) }
//  }

  // implicit def tuple4BifunctorXaXb[X1,X2]: Bifunctor[(X1,*,X2,*)] = ??? // TODO
  // implicit def tuple4BifunctorXabX[X1,X2]: Bifunctor[(X1,*,*,X2)] = ??? // TODO
  // implicit def tuple4BifunctoraXXb[X1,X2]: Bifunctor[(*,X1,X2,*)] = ??? // TODO
  // implicit def tuple4BifunctoraXbX[X1,X2]: Bifunctor[(*,X1,*,X2)] = ??? // TODO
  // implicit def tuple4BifunctorabXX[X1,X2]: Bifunctor[(*,*,X1,X2)] = ??? // TODO
}
