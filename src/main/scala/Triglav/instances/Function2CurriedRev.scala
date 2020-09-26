package Triglav.instances

import Triglav.face3.Trifunctor

object Function2CurriedRev {

  type CurriedRev[-B, +C, +A] = (C => B) => A

  def curriedRevTriglav: Trifunctor[CurriedRev] = new Trifunctor[CurriedRev] {

    override def timap[B, C, A, BB, CC, AA](
        fa: (C => B) => A
    )(f: BB => B, g: C => CC, h: A => AA): (CC => BB) => AA =
      k => h(fa(g andThen k andThen f))

    override def dimapLeft[B, C, A, BB, CC](
        fa: (C => B) => A
    )(f: BB => B, g: C => CC): (CC => BB) => A =
      k => fa(g andThen k andThen f)

    override def dimap[B, C, A, BB, AA](
        fa: (C => B) => A
    )(f: BB => B, h: A => AA): (C => BB) => AA =
      k => h(fa(k andThen f))

    override def mapLeft[B, C, A, CC](fa: (C => B) => A)(
        g: C => CC
    ): (CC => B) => A =
      k => fa(g andThen k)

    override def contramap[B, C, A, BB](fa: (C => B) => A)(
        f: BB => B
    ): (C => BB) => A =
      k => fa(k andThen f)

    override def bimap[B, C, A, CC, AA](
        fa: (C => B) => A
    )(g: C => CC, h: A => AA): (CC => B) => AA =
      k => h(fa(g andThen k))

    override def map[B, C, A, AA](fa: (C => B) => A)(
        h: A => AA
    ): (C => B) => AA =
      fa andThen h
  }
}
