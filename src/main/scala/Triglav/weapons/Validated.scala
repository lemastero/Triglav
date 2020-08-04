package Triglav.weapons

case class HeadNel[+A](head: A, tail: AbstractNel[A]) extends AbstractNel[A]

sealed trait AbstractNel[+A]{
  def head: A

  def tailOpt: Option[AbstractNel[A]] = this match {
    case HeadNel(_, tail) => Some(tail)
    case _ => None
  }
}

sealed trait VN[+A, +E]
sealed trait V[+A, +E] extends VN[A,E]
case class SuccessV[A](result: A) extends V[A, Nothing]
case class ErrorV[E](error: E) extends V[Nothing, E]

case class ErrorsV[E](error: E, errors: HeadNel[E]) extends VN[Nothing, E]
