package Triglav.instances

import Triglav.face2.Bifunctor

object EitherInc {
  type \/[+A, +B] = Either[A, B]

  def eitherBifunctor: Bifunctor[\/] = new Bifunctor[\/] {

    override def bimap[AA, A, B, BB](
        fa: A \/ B
    )(f: A => AA, g: B => BB): AA \/ BB =
      fa match {
        case Left(e)  => Left(f(e))
        case Right(v) => Right(g(v))
      }
  }
}
