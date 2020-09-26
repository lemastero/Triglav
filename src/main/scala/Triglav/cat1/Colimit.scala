package Triglav.cat1

trait Colimit

/** Colimits are unique up to isomorphism */
object Colimit {

  trait CoProduct extends Colimit {
    type Coproduct[+A, +B]
    def left[A, B]: A => Coproduct[A, B]
    def right[A, B]: B => Coproduct[A, B]
  }

  trait InitialObject extends Colimit {
    type Initial
    def absurd[A]: Initial => A
  }

  abstract class PushOut[A, B, -C](val f: C => A, val g: C => B) extends Limit {
    type U
    def q: A => U
    def p: B => U

    def inPushOut(c: C): Boolean =
      (f andThen q)(c) == (g andThen p)(c)
  }

  abstract class CoEqualizer[-X, Y](val f: X => Y, val g: X => Y)
      extends Limit {
    type E
    def eq: Y => E

    def inCoEqualizer(x: X): Boolean =
      (f andThen eq)(x) == (g andThen eq)(x)
  }
}
