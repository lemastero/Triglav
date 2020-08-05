package Triglav.cat1

trait Semicategory[Morphism[_,_]] {
  def compose[A,B,C](f: Morphism[B,C])(g: Morphism[A,B]): Morphism[A,C]
}

trait SemicategoryLaws[M[_,_]] extends Semicategory[M] {

  def compositivityLaw[A,B,C,D](g: M[B,C], f: M[A,B], h:M[C,D]): Boolean = {
    val gf: M[A, C] = compose(g)(f)
    val v2: M[A, D] = compose(h)(gf)

    val hg: M[B, D] = compose(h)(g)
    val w2: M[A, D] = compose(hg)(f)

    v2 == w2
  }
}
