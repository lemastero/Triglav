package Triglav.cat1

trait Limit

/** Limits are unique up to isomorphism */
object Limit {

  /** Categorical Product
    *
    * Product is limit with diagram containing 2 points and no morphisms.
    */
  trait Product extends Limit {
    type Product[+A, +B]
    def fst[A, B]: Product[A, B] => A
    def snd[A, B]: Product[A, B] => B
  }

  /** Terminal object (final object)
    *
    * Given C be a Category. Terminal object in C is object T such that
    * (universal property) given any other object X there is unique morphism
    * from X to terminal object.
    *
    * X -----> T
    *
    * Examples:
    *   - Set: every set with 1 element e.g. {42}
    *   - Grp: trivial group
    *   - Top: one point space
    *   - Poset: maximum element (if exists)
    *
    * Non examples:
    *   - Natural numbers (no max)
    *   - Vector spaces
    *
    * Terminal objects in C are unique up to unique isomorphism.
    *
    * Terminal object is limit over empty diagram.
    */
  trait TerminalObject extends Limit {
    type Terminal
    def unit[A]: A => Terminal
  }

  /** Given: object C and morphisms: A => C and B => C
    *
    *           B
    *           |
    *           |   g
    *          \/
    * A -----> C
    *     f
    *
    * a pull back (or fiber product) is:
    * - object U
    * - morphisms U => B and U => A
    *
    *       p
    *  U ------> B
    *  |         |
    *  |  q      |   g
    *  \/        \/
    *  A ------> C
    *      f
    *
    * such that (universal property of pullback)
    * for all other V, s: V => B, t: V => A
    *
    *        t
    *   V -------------+
    *   |              |
    *   |        p    \/
    *   |    U ------> B
    * s |    |         |
    *   |  q |         |   g
    *   |    \/  f     \/
    *   +---> A ------> C
    *
    * exists unique morphism h: V => U such that
    * following diagrams commute:
    *
    *        t
    *   V -------------+
    *   | \            |
    *   |  \/     p    \/
    *   |    U ------> B
    * s |    |         |
    *   |  q |         |   g
    *   |    \/  f     \/
    *   +---> A ------> C
    *            f
    *
    * Pullback is limit where diagram has 3 objects A, B, C
    * and morphisms A => C and B => C
    *        B
    *        |
    *        \/
    * A ---> C
    */
  abstract class Pullback[A, B, +C](val f: A => C, val g: B => C)
      extends Limit {
    type U
    def q: U => A
    def p: U => B

    def inPullback(u: U): Boolean =
      (q andThen f)(u) == (p andThen g)(u)
  }

  /** Equalizer is a limit where diagram has 2 objects A, B and 2 morphisms A =>
    * B
    *
    * ---> A ---> B
    */
  abstract class Equalizer[X, +Y](val f: X => Y, val g: X => Y) extends Limit {
    type E
    def eq: E => X

    def inEqualizer(x: E): Boolean =
      (eq andThen f)(x) == (eq andThen g)(x)
  }
}
