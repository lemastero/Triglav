package Triglav.incarnations

import Triglav.face2.Bifunctor

object EitherInc {
  type \/[+A,+B] = Either[A,B]

  def eitherBifunctor: Bifunctor[\/] = new Bifunctor[\/] {
    override def map[L,R,RR](e: L \/ R)(f: R => RR): L \/ RR =
      e.map(f)

    override def mapLeft[A,AA,B](fa: A \/ B)(f: A => AA): AA \/ B =
      fa match {
        case Left(e) => Left(f(e))
        case Right(v) => Right(v)
      }
  }
}
