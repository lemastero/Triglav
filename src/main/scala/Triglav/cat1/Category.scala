package Triglav.cat1

trait Category[Morphism[_, _]] extends Semicategory[Morphism] {
  def id[Obj]: Morphism[Obj, Obj]
}

trait CategoryLaws[M[_, _]] extends Category[M] {
  def leftIdentityLaw[A, B](fa: M[A, B]): Boolean = {
    compose(id[B])(fa) == fa
  }

  def rightIdentityLaw[A, B](fa: M[A, B]): Boolean = {
    compose(fa)(id[A]) == fa
  }
}
