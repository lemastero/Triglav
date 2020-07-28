package Triglav

object eye {
  type Optics[P[_,_],S,T,A,B] = P[A,B] => P[S,T]
}
