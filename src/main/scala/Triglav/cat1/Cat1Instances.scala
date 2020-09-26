package Triglav.cat1

object Cat1Instances {

  abstract final class Void {
    def absurd[A]: A
  }

  object Product extends Limit.Product {
    type Product[+A, +B] = (A, B)
    def fst[A, B]: Product[A, B] => A = _._1
    def snd[A, B]: Product[A, B] => B = _._2
  }

  object TerminalObject extends Limit.TerminalObject {
    type Terminal = Unit
    def unit[A]: A => Terminal = _ => ()
  }

  object InitialObject extends Colimit.InitialObject {
    type Initial = Void
    def absurd[A]: Initial => A = _.absurd[A]
  }

  object Coproduct extends Colimit.CoProduct {
    type Coproduct[+A, +B] = Either[A, B]
    def left[A, B]: A => Coproduct[A, B] = Left(_)
    def right[A, B]: B => Coproduct[A, B] = Right(_)
  }

  trait Function1Cat
      extends FinitelyCompleteCategory[Function1]
      with FinitelyCoCompleteCategory[Function1] {

    def id[A]: A => A = identity[A]
    def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f

    val Terminal = Cat1Instances.TerminalObject
    val Product = Cat1Instances.Product

    val Initial = Cat1Instances.InitialObject
    val Coproduct = Cat1Instances.Coproduct
  }

  // Category of Scala proper types and pure Function1's
  val TyFun1Cat: Function1Cat = new Function1Cat {}
}
