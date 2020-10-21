## Trifunctors

C`L`owns to the Left, Joke`R`s to the right and `B`ards to the back. F[E,A,R] the fun trio!


### origins

Inspired by [Clowns to the left of me, jokers to the right (pearl): dissecting data structures - Conor McBride](https://personal.cis.strath.ac.uk/conor.mcbride/Dissect.pdf)

Haskell define wrappers that implement Functor map for different type:
 * [Clown](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Clown.html)
 * [Joker](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Joker.html)
 
Cats allow you to convert [Bifunctor into LeftFunctor or RightFunctor](https://github.com/typelevel/cats/pull/1847/files)

What if ... we actually have implementation on Functor for type constructor with 2 holes that ignore one hole?

### abstractions with 2 types

```scala
// law: mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g
trait Clown[F[_, _]] {
  def mapLeft[A, AA, B](fa: F[A, B])(g: A => AA): F[AA, B]
}

// law: map (f compose g) ≡ map f compose map g
trait Joker[F[_, _]] {
  def map[A, B, BB](fa: F[A, B])(g: B => BB): F[A, BB]
}
```

We can define using them Bifunctor

```scala

// laws:
// bimap  (f compose g) (h compose i) ≡ bimap f h compose bimap g i
// mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g - inherited from Clown
// map (f compose g) ≡ map f compose map g - inherited from Joker
trait Bifunctor[F[_, _]] extends Joker[F] with Clown[F] {

  def bimap[AA, A, B, BB](fa: F[A, B])(f: A => AA, g: B => BB): F[AA, BB] = {
    val v1: F[A, BB] = map(fa)(g)
    val v2: F[AA, BB] = mapLeft(v1)(f)
    v2
  }
}
```

to the classic duet of Joker and Clown we can add a third player, that goes contra to the current music trends - Bard:

```scala
// law: contramap (f andThen g) ≡ contramap f andThen contramap g
trait Bard[F[_, _]] {
  def contramap[A, AA, B](fa: F[A, B])(f: AA => A): F[AA, B]
}
```

now we can define Profunctor

```scala
// laws:
// dimap  (f andThen g) (h andThen i) ≡ dimap f h andThen bimap g i
// map (f compose g) ≡ map f compose map g -- from Joker
// contramap (f andThen g) ≡ contramap f andThen contramap g -- from Bard
trait Profunctor[F[_, _]] extends Joker[F] with Bard[F] {

  def dimap[AA, A, B, BB](fa: F[A, B])(f: AA => A, g: B => BB): F[AA, BB] =
    map(contramap(fa)(f))(g)
}
```

Following implementation of Profunctor and Bifunctor in Scala have following benefits
 * `modularity` (express two more complex structures using simpler ones)
 * Joker definition refactors `common part` of Bifuctor and Profunctor
   - common `vocabulary` for method that is common for Bifunctor and Profunctor
   - common `laws` for Bifunctor and Profunctor
 * `three new absractions` with nice set of laws! We can use where we can't use Bifunctor or Profunctor (`zio.Fiber`)

### abstractions with 3 types

in Haskell [snoyberg//trio](https://github.com/snoyberg/trio)
in Scala [ZIO](https://github.com/zio/zio)

Explore type constructors with three holes:

method sigantures are similar like in versions with 2 holes:

```scala
trait TriBard[F[_, _, _]] {
  def contramap[E, A, R, EE](fa: F[E, A, R])(f: EE => E): F[EE, A, R]
}

trait TriClown[F[_, _, _]] {
  def mapLeft[E, A, R, AA](fa: F[E, A, R])(g: A => AA): F[E, AA, R]
}

trait TriJoker[F[_, _, _]] {
  def map[A, E, R, RR](fa: F[A, E, R])(h: R => RR): F[A, E, RR]
}
```

now we can express Biffunctor with 3-hole type constructor

```scala
trait TriBiff[F[_, _, _]] extends TriClown[F] with TriJoker[F] {

  def bimap[E, A, R, AA, RR](
    fa: F[E, A, R]
  )(g: A => AA, h: R => RR): F[E, AA, RR]
}
```

two different variants of Profunctor with 3 params:

```scala
trait TriProfJoker[F[_, _, _]] extends TriBard[F] with TriJoker[F] {

  def dimap[E, A, R, EE, RR](
    fa: F[E, A, R]
  )(f: EE => E, h: R => RR): F[EE, A, RR]
}

trait TriProfClown[F[_, _, _]] extends TriBard[F] with TriClown[F] {

  def dimapLeft[E, A, R, EE, AA](
    fa: F[E, A, R]
  )(f: EE => E, g: A => AA): F[EE, AA, R]
}
```

and final abstraction [FunTrio](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/face3/Trifunctor.scala)

```scala
trait FunTrio[F[_, _, _]]
  extends TriBard[F]
    with TriClown[F]
    with TriJoker[F]
    with TriBiff[F]
    with TriProfClown[F]
    with TriProfJoker[F] {

  def timap[E, A, R, EE, AA, RR](
    fa: F[E, A, R]
  )(f: EE => E, g: A => AA, h: R => RR): F[EE, AA, RR]
}
```

that happen to be the one abstraction to rule them all.


Many [ZIO abstractions fit into Trifunctor](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/instances/TrifunctorInstancesZIO.scala)
avoid define multiple instances when abstract over 2 types.

* [Add to zio-prelude](https://github.com/zio/zio-prelude/issues/291)

* build `zio-effects` like cats-effects but for ZIO? (distage? izumi?) already done it?

* can we have common abstractions and support Monix, Monix BIO?
```scala
type RIO[E, A, R] = A => Either[E, monix.Task[R]]
```

* WIP shapes from Optics (especially profunctor optics) fit into Trifunctor and Nifunctor 

* [ZPure](https://github.com/zio/zio-prelude/blob/master/src/main/scala/zio/prelude/fx/ZPure.scala)
