package Triglav.tambara

trait Glassed[=>:[-_,+_]] extends Strong[=>:] with Closed[=>:] { // TambaraModule (T, U => _)
  def glassedL[A,B,U,T]: (A =>: B) => ((T, U => A) =>: (T, U => B)) =
    second compose closed
  def glassedR[A,B,U,T]: (A =>: B) => ((U => A,T) =>: (U => B,T)) =
    first compose closed
}
