package Triglav.cat1

trait Category[Morphism[_,_]] extends Semicategory[Morphism] {
  def id[Obj]: Morphism[Obj,Obj]
}

trait CategoryLaws[M[_,_]] extends Category[M] {
  def leftIdentityLaw[A,B](fa: M[A,B]): Boolean = {
    compose(id[B])(fa) == fa
  }

  def rightIdentityLaw[A,B](fa: M[A,B]): Boolean = {
    compose(fa)(id[A]) == fa
  }
}

object CategoryInstances {
  trait Function1Cat extends Category[Function1] {
    def id[A]: A => A = identity[A]
    def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f
  }

  val scalaProperTypesAndPureFunction1: Category[Function1] = new Function1Cat {}
}
