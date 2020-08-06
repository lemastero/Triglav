package Triglav.cat1

object CategoryInstances {

  abstract final class Void {
    def absurd[A]: A
  }

  trait Function1Cat extends FinitelyCompleteCategory[Function1] {
    def id[A]: A => A = identity[A]
    def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f

    type Terminal = Unit
    def unit[A]: A => Terminal = _ => ()

    type Initial = Void
    def absurd[A]: Initial => A = _.absurd[A]

    type Product[+A,+B] = (A,B)
    def fst[A,B]: Product[A,B] => A = _._1
    def snd[A,B]: Product[A,B] => B = _._2

    type Coproduct[+A,+B] = Either[A,B]
    def left[A,B]: A => Coproduct[A,B] = Left(_)
    def right[A,B]: B => Coproduct[A,B] = Right(_)
  }

  // Category of Scala proper types and pure Function1's
  val TyFun1Cat: Function1Cat = new Function1Cat {}
}
