package Triglav.cat2

trait TwoCategory[OneCell[_[_], _[_]]] {
  def id[Obj[_]]: OneCell[Obj, Obj]
  def compose[ObA[_], ObB[_], ObC[_]](f: OneCell[ObB, ObC])(
      g: OneCell[ObA, ObB]
  ): OneCell[ObA, ObC]

  type TwoCell[F[_], G[_]] = OneCell[F, G] => OneCell[F, G]
}

trait TwoCategoryLaws[OneCell[_[_], _[_]]] extends TwoCategory[OneCell] {
  def leftIdentityLaw[A[_], B[_]](fa: OneCell[A, B]): Boolean = {
    compose(id[B])(fa) == fa
  }

  def rightIdentityLaw[A[_], B[_]](fa: OneCell[A, B]): Boolean = {
    compose(fa)(id[A]) == fa
  }

  def compositivityLaw[A[_], B[_], C[_], D[_]](
      g: OneCell[B, C],
      f: OneCell[A, B],
      h: OneCell[C, D]
  ): Boolean = {
    val gf: OneCell[A, C] = compose(g)(f)
    val v2: OneCell[A, D] = compose(h)(gf)

    val hg: OneCell[B, D] = compose(h)(g)
    val w2: OneCell[A, D] = compose(hg)(f)

    v2 == w2
  }
}
