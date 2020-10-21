# Triglav

RnD project exploring in Scala:
- modular and unified approach to abstractions in category theory ([bifunctors](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/face2/Bifunctor.scala) and [profunctors](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/face2/Profunctor.scala))
- [profunctor optics](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/optics/ProfunctorOptics.scala), include profunctors that match [tambara module](https://github.com/lemastero/Triglav/tree/master/src/main/scala/Triglav/tambara) WIP
- [trifunctors](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/face3/Trifunctor.scala) and [Nifuctor](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/face3/Nifunctor.scala)
- category theory encoding with goal to get useful abstractions/utilities
  - [semicategory](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat1/Semicategory.scala)
  - [category](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat1/Category.scala)
  - [limits](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat1/Limit.scala): terminal object, categorical product, pullback, equalizer
  - [colimits](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat1/Colimit.scala): initial object, coproduct, pushout, coequalizer
  - [finitely complete category](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat1/FinitelyCompleteCategory.scala) ([nlab](https://ncatlab.org/nlab/show/finitely+complete+category))
  - [finitely cocomplete category](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat1/FinitelyCoCompleteCategory.scala) ([nlab](https://ncatlab.org/nlab/show/finitely+cocomplete+category))
  - [monoidal categoriy](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/monoidal1/MonoidalCategory.scala)
  - [braided monoidal category](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/monoidal1/BraidedMonoidalCategory.scala)
  - [symmetric monoidal category](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/monoidal1/SymmetricMonoidalCategory.scala)
  - categories of profunctors
  - [2-category](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat2/TwoCategory.scala) of type constructors and [natural transformation](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/cat2/NaturalTransformation.scala) WIP
  - [category of profunctors](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/catpro/ProCategory.scala) and [dinatural transformations](https://github.com/lemastero/Triglav/blob/master/src/main/scala/Triglav/catpro/ProNaturalTransformation.scala)
  - toposes WIP
  
This repo intends to create base for further research about: automatic code completition, optimization of functional proramms, generating FP libraries targetted for particular problem domain.
