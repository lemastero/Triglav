package Triglav.tambara

import Triglav.face2.Profunctor

trait Closed[=>:[-_,+_]] extends Profunctor[=>:] { // TambaraModule C => _
  def closed[A,B,C]: A=>:B => (C=>A) =>: (C => B)
}
