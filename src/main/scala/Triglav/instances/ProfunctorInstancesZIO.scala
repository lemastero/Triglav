package Triglav.instances

import Triglav.face2.Profunctor
import zio.stream.ZStream
import zio.{ZIO, ZLayer, ZManaged}


object ProfunctorInstancesZIO {

  implicit val Function1Profunctor: Profunctor[Function1] =
    new Profunctor[Function1] {
      override def dimap[S, T, A, B](ab: A => B)(sa: S => A, bt: B => T): S => T =
        sa andThen ab andThen bt
    }

  // ZIO[-R, +E, +A]

  implicit def ZIOProfunctor[E]: Profunctor[ZIO[*, E, *]] =
    new Profunctor[ZIO[*, E, *]] {
      override def dimap[RR, AA, R, A](pab: ZIO[R, E, A])(sa: RR => R, bt: A => AA): ZIO[RR, E, AA] =
        pab.map(bt).provideSome(sa)
    }

  implicit def ZIOProfunctorError[A]: Profunctor[ZIO[*, *, A]] =
    new Profunctor[ZIO[*, *, A]] {
      override def dimap[RR, EE, R, E](pab: ZIO[R, E, A])(sa: RR => R, bt: E => EE): ZIO[RR, EE, A] =
        pab.mapError(bt).provideSome(sa)
    }

  // ZLayer[-RIn, +E, +ROut]

  implicit def ZLayerProfunctor[E]: Profunctor[ZLayer[*, E, *]] =
    new Profunctor[ZLayer[*, E, *]] {
      override def dimap[S, T, A, B](pab: ZLayer[A, E, B])(f: S => A, g: B => T): ZLayer[S, E, T] =
        ZLayer.fromFunctionMany(f) >>> pab.map(g)
    }

  implicit def ZLayerProfunctorError[ROut]: Profunctor[ZLayer[*, *, ROut]] =
    new Profunctor[ZLayer[*, *, ROut]] {
      override def dimap[S, T, A, B](pab: ZLayer[A, B, ROut])(f: S => A, g: B => T): ZLayer[S, T, ROut] =
        ZLayer.fromFunctionMany(f) >>> pab.mapError(g)
    }

  // ZManaged[-R, +E, +A]

  implicit def ZManagedProfunctor[EE]: Profunctor[ZManaged[*, EE, *]] =
    new Profunctor[ZManaged[*, EE, *]] {
      override def dimap[S, T, A, B](pab: ZManaged[A, EE, B])(sa: S => A, bt: B => T): ZManaged[S, EE, T] =
        pab.map(bt).provideSome(sa)
    }

  implicit def ZManagedProfunctorError[AA]: Profunctor[ZManaged[*, *, AA]] =
    new Profunctor[ZManaged[*, *, AA]] {
      override def dimap[S, T, A, B](pab: ZManaged[A, B, AA])(sa: S => A, bt: B => T): ZManaged[S, T, AA] =
        pab.mapError(bt).provideSome(sa)
    }

  // ZStream[-R, +E, +O]

  implicit def ZStreamProfunctor[E]: Profunctor[ZStream[*, E, *]] =
    new Profunctor[ZStream[*, E, *]] {
      override def dimap[S, T, A, B](pab: ZStream[A, E, B])(sa: S => A, bt: B => T): ZStream[S, E, T] =
        pab.map(bt).provideSome(sa)
    }
}
