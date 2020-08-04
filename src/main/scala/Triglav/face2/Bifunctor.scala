package Triglav.face2

// laws:
// bimap  (f compose g) (h compose i) ≡ bimap f h compose bimap g i
// mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g - inherited from Clown
// map (f compose g) ≡ map f compose map g - inherited from Joker
trait Bifunctor[:=:>[+_,+_]] extends Joker[:=:>] with Clown[:=:>] {

  def bimap[AA,A,B,BB](fa: A :=:> B)(f: A => AA, g: B => BB): AA :=:> BB = {
    val v1: A :=:> BB = map(fa)(g)
    mapLeft(v1)(f)
  }
}

trait BifunctorLaws[P[+_,+_]]
  extends JokerLaws[P]
    with ClownLaws[P]
    with Bifunctor[P] {

  // dimap id id == id
  def bimapIdentity[A,B](p: P[A,B]): Boolean = {
    //          bimap(id, id)
    // P[A,B] ================> P[A,B]
    bimap(p)(identity[A], identity[B]) == p
  }

  // bimap (g . f) (h . i) == bimap g h . bimap f i
  def bimapComposition[A,A2,A3,B,B2,B3](pad: P[A,B], g: A2 => A3, f: A => A2, i: B => B2, h: B2 => B3): Boolean = {
    //          bimap A=>A2 B=>B2
    // P[A,B] ===================> F[A2,B2]
    val p2: P[A2,B2] = bimap(pad)(f,i)
    //          dimap A2=>A3 B2=>B3
    // P[A2,B2] ====================> P[A3,B3]
    val l: P[A3,B3] = bimap(p2)(g,h)

    val fg: A => A3 = g compose f
    val hi: B => B3 = h compose i
    //         bimap A3=>A B=>B3
    // P[A,B] ===================> P[A3,B3]
    val r: P[A3,B3] = bimap(pad)(fg, hi)

    l == r
  }

  // dimap f g == contramap (map g) f
  def bimapCoherentWithMapAndLeftamap[A,A2,A3,B,B2,B3](pad: P[A,B], f: A => A2, g: B => B2): Boolean = {
    //          bimap A2=>A B=>B2
    // P[A,B] ===================> F[A2,B2]
    val l: P[A2,B2] = bimap(pad)(f,g)

    val r = mapLeft(map(pad)(g))(f)

    l == r
  }
}
