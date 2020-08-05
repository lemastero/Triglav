package Triglav.catpro

import Triglav.catpro.IdentityDinat.IdentityDinat

trait ProSemicategory[Morphism[_[_,_],_[_,_]]] {
  def compose[A[_,_],B[_,_],C[_,_]](f: Morphism[B,C])(g: Morphism[A,B]): Morphism[A,C]
}

trait ProSemicategoryLaws[M[_[_,_],_[_,_]]] extends ProSemicategory[M] {

  def compositivityLaw[A[_,_],B[_,_],C[_,_],D[_,_]](g: M[B,C], f: M[A,B], h: M[C,D]): Boolean = {
    val gf: M[A, C] = compose(g)(f)
    val v2: M[A, D] = compose(h)(gf)

    val hg: M[B, D] = compose(h)(g)
    val w2: M[A, D] = compose(hg)(f)

    v2 == w2
  }
}

trait ProCategory[Morphism[_[_,_],_[_,_]]] extends ProSemicategory[Morphism] {
  def id[Obj[_,_]]: Morphism[Obj,Obj]
}

trait ProCategoryLaws[M[_[_,_],_[_,_]]] extends ProCategory[M] {
  def leftIdentityLaw[A[_,_],B[_,_]](fa: M[A,B]): Boolean = {
    compose(id[B])(fa) == fa
  }

  def rightIdentityLaw[A[_,_],B[_,_]](fa: M[A,B]): Boolean = {
    compose(fa)(id[A]) == fa
  }
}

object ProCategoryInstances {
  trait DinatCat extends ProCategory[~~>] {
    override def id[Obj[_,_]]: Obj ~~> Obj = IdentityDinat[Obj]
    override def compose[A[_,_], B[_,_], C[_,_]](f: B ~~> C)(g: A ~~> B): A ~~> C = f compose g
  }

  val scalaTypeConstructorsAndNaturalTransf: ProCategory[~~>] = new DinatCat {}
}

