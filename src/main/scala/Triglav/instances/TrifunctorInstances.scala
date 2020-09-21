package Triglav.instances

import Triglav.face3.Trifunctor
import cats.MonadError
import cats.effect.IO

object TrifunctorInstances {

  // A => B[L,R] where B is Bifunctor
  type FunctionToEither[A, L, R] = A => Either[L, R]
  val funTrioFunctionToEither: Trifunctor[FunctionToEither] = new Trifunctor[FunctionToEither] {
    override def timap[E, A, R, EE, AA, RR](fa: FunctionToEither[E, A, R])(f: EE => E, g: A => AA, h: R => RR): FunctionToEither[EE, AA, RR] =
      ???
  }

  type FunctionTuple2[A, L, R] = A => (L, R)
  val funTrioFunctionToTuple: Trifunctor[FunctionTuple2] = new Trifunctor[FunctionTuple2] {
    override def timap[E, A, R, EE, AA, RR](fa: FunctionTuple2[E, A, R])(f: EE => E, g: A => AA, h: R => RR): FunctionTuple2[EE, AA, RR] = ???
  }

  // more on this on my rant on Twitter https://twitter.com/pparadzinski/status/1205202077238091776
  type CurriedRev[-B,+C,+A] = (C => B) => A

  def curriedRevTriglav: Trifunctor[CurriedRev] = new Trifunctor[CurriedRev] {

    override def timap[B,C,A,BB,CC,AA](fa: (C => B) => A)(f: BB => B, g: C => CC, h: A => AA): (CC => BB) => AA =
      k => h(fa(g andThen k andThen f))

    // combinators from Trifunctor:
    //
    //    override def dimapLeft[B,C,A,BB,CC](fa: (C => B) => A)(f: BB => B, g: C => CC): (CC => BB) => A =
    //      k => fa(g andThen k andThen f)
    //
    //    override def dimap[B,C,A,BB,AA](fa: (C => B) => A)(f: BB => B, h: A => AA): (C => BB) => AA =
    //      k => h(fa(k andThen f))
    //
    //    override def mapLeft[B,C,A,CC](fa: (C => B) => A)(g: C => CC): (CC => B) => A =
    //      k => fa(g andThen k)
    //
    //    override def contramap[B,C,A,BB](fa: (C => B) => A)(f: BB => B): (C => BB) => A =
    //      k => fa(k andThen f)
    //
    //    override def bimap[B,C,A,CC,AA](fa: (C => B) => A)(g: C => CC, h: A => AA): (CC => B) => AA =
    //      k => h(fa(g andThen k))
    //
    //    override def map[B,C,A,AA](fa: (C => B) => A)(h: A => AA): (C => B) => AA =
    //      fa andThen h
  }

  // see instances for ZIO
  type RIO[E, A, R] = A => Either[E, IO[R]]  // TODO embedding of cats IO/monix Task into FunTrio

  //TODO embedding cats-mtl into FunTrio
  type MonadError3[F[_], E, A, R] = A => MonadError[F, E]
  def funTrioIO[F[_]]: Trifunctor[MonadError3[F,*,*,*]] = new Trifunctor[MonadError3[F,*,*,*]] {
    override def timap[E, A, R, EE, AA, RR](fa: MonadError3[F, E, A, R])(f: EE => E, g: A => AA, h: R => RR): MonadError3[F, EE, AA, RR] = ???
  }
}
