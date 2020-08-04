package Triglav.tambara

trait Magnified[=>:[-_,+_]] extends CartesianStrong[=>:] with CoCartesianChoice[=>:] { // TambaraModule (C, Either[_,D])

  def maginfyLS[A,B,C,D]: (A =>: B) => ((C, Either[A,D]) =>: (C, Either[B,D])) =
    second[Either[A,D], Either[B,D], C] compose left[A,B,D]

  def maginfyRS[A,B,C,D]: (A =>: B) => ((C, Either[D,A]) =>: (C, Either[D,B])) =
    second[Either[D,A], Either[D,B], C] compose right[A,B,D]

  def maginfyLF[A,B,C,D]: (A =>: B) => ((Either[A,D],C) =>: (Either[B,D],C)) =
    first[Either[A,D], Either[B,D], C] compose left[A,B,D]

  def maginfyRF[A,B,C,D]: (A =>: B) => ((Either[D,A],C) =>: (Either[D,B],C)) =
    first[Either[D,A], Either[D,B], C] compose right[A,B,D]
}

trait MagnifiedLaws[P[-_,+_]]
  extends Magnified[P]
    with CartesianStrong[P]
    with CoCartesianChoice[P]
