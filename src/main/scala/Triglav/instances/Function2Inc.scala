package Triglav.instances

import Triglav.face3.Fnfunctor

object Function2Inc {

  def function2Norne: Fnfunctor[Function2] = new Fnfunctor[Function2] {

    override def fnmap[E,A,R,EE,AA,RR](fa: (E,A) => R)(f: EE => E, g: AA => A, h: R => RR): (EE,AA) => RR =
      { case(ee,aa) => h(fa(f(ee),g(aa))) }

    override def contramap[E, A, R, EE](fa: (E, A) => R)(
        f: EE => E
    ): (EE, A) => R = { case (ee, a) => fa(f(ee), a) }

    override def contramapLeft[E, A, R, AA](fa: (E, A) => R)(
        g: AA => A
    ): (E, AA) => R = { case (e, aa) => fa(e, g(aa)) }

    override def map[A, E, R, RR](fa: (A, E) => R)(h: R => RR): (A, E) => RR = {
      case (a, e) => h(fa(a, e))
    }
  }
}
