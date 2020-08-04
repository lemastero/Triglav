package Triglav.tambara

trait Windowed[=>:[-_,+_]] extends CoCartesianChoice[=>:] with Glassed[=>:] {
  def windowedRGL[A,B,U,V,T]: (A =>: B) => (Either[V, (T,U=>A)] =>: Either[V, (T,U=>B)]) =
    right compose glassedL
  def windowedRGR[A,B,U,V,T]: (A =>: B) => (Either[V, (U=>A,T)] =>: Either[V, (U=>B,T)]) =
    right compose glassedR
  def windowedLGL[A,B,U,V,T]: (A =>: B) => (Either[(U=>A,T), V] =>: Either[(U=>B,T), V]) =
    left compose glassedR
  def windowedLGR[A,B,U,V,T]: (A =>: B) => (Either[(U=>A,T),V] =>: Either[(U=>B,T),V]) =
    left compose glassedR
}
