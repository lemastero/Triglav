package Triglav.tambara

trait Magnified[=>:[-_,+_]] extends Strong[=>:] with Choice[=>:] { // TambaraModule (C, Either[_,D])
  def maginfyLS[A,B,C,D]: (A =>: B) => ((C, Either[A,D]) =>: (C, Either[B,D])) =
    second compose left
  def maginfyRS[A,B,C,D]: (A =>: B) => ((C, Either[D,A]) =>: (C, Either[D,B])) =
    second compose right
  def maginfyLF[A,B,C,D]: (A =>: B) => ((Either[A,D],C) =>: (Either[B,D],C)) =
    first compose left
  def maginfyRF[A,B,C,D]: (A =>: B) => ((Either[D,A],C) =>: (Either[D,B],C)) =
    first compose right
}
