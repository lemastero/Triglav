package Triglav.monoidal1

trait SymmetricMonoidalCategoryLaws[:=>[_,_],⊗[+_,+_], I]
  extends BraidedMonoidalCategory[:=>,⊗, I]
    with BraidedMonoidalCategoryLaws[:=>,⊗, I] {

  def symmetry[A,B](fa: A⊗B): Boolean = {
    braiding(braiding(fa)) == fa
  }
}
