package Triglav.optics.classic

object GeneralizedOpics {
  case class Adapter   [-S,+T,+A,-B](       from:   S => A,            to:     B     => T )
  case class Prism     [-S,+T,+A,-B](       matsh:  S => Either[T,A],  revGet: B     => T )
  case class Lens      [-S,+T,+A,-B](       view:   S => A,            update: (B,S) => T )
  case class Traversal [-S,+T,+A,-B](    extract:   S => FunList[A,B,T]                   )
  case class Optional  [-S,+T,+A,-B](        get:   S => Option[A],    set:    (B,S) => T )
}
