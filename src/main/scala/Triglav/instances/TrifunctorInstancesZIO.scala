package Triglav.instances

import Triglav.face3.Zifunctor
import zio.stm.ZSTM
import zio.{ZIO, ZLayer, ZManaged}
import zio.stream.ZStream

object TrifunctorInstancesZIO {

  // ZIO[-R, +E, +A]

  implicit val ZioTrifunctor: Zifunctor[ZIO] = new Zifunctor[ZIO] {
    override def zimap[E, A, R, EE, AA, RR](
        fa: ZIO[E, A, R]
    )(f: EE => E, g: A => AA, h: R => RR): ZIO[EE, AA, RR] =
      fa.mapBoth(g, h).provideSome(f)
  }

  // ZLayer[-RIn, +E, +ROut]

  implicit val ZLayerTrifunctor: Zifunctor[ZLayer] =
    new Zifunctor[ZLayer] {
      override def zimap[E, A, R, EE, AA, RR](
          fa: ZLayer[E, A, R]
      )(f: EE => E, g: A => AA, h: R => RR): ZLayer[EE, AA, RR] =
        ZLayer.fromFunctionMany(f) >>> fa.map(h).mapError(g)
    }

  // ZManaged[-R, +E, +A]

  implicit val ZManagedTrifunctor: Zifunctor[ZManaged] =
    new Zifunctor[ZManaged] {
      override def zimap[E, A, R, EE, AA, RR](
          fa: ZManaged[E, A, R]
      )(f: EE => E, g: A => AA, h: R => RR): ZManaged[EE, AA, RR] =
        fa.mapBoth(g, h).provideSome(f)
    }

  // ZStream[-R, +E, +O]

  implicit val ZStreamTrifunctor: Zifunctor[ZStream] =
    new Zifunctor[ZStream] {
      override def zimap[E, A, R, EE, AA, RR](
          fa: ZStream[E, A, R]
      )(f: EE => E, g: A => AA, h: R => RR): ZStream[EE, AA, RR] =
        fa.bimap(g, h).provideSome(f)
    }

  // ZSTM[-R, +E, +A]

  implicit val ZSTMTrifunctor: Zifunctor[ZSTM] =
    new Zifunctor[ZSTM] {
      override def zimap[E, A, R, EE, AA, RR](
          fa: ZSTM[E, A, R]
      )(f: EE => E, g: A => AA, h: R => RR): ZSTM[EE, AA, RR] =
        fa.mapBoth(g, h).provideSome(f)
    }
}
