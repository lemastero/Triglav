package Triglav.instances

import Triglav.face3.Nifunctor
import zio.Schedule

object NiFunctorInstancesZIO {

  // zio.Schedule[-Env, -In, +Out]

  implicit val ScheduleNifunctor: Nifunctor[Schedule] =
    new Nifunctor[Schedule] {
      override def nimap[E, A, R, EE, AA, RR](
          fa: Schedule[E, A, R]
      )(f: EE => E, g: AA => A, h: R => RR): Schedule[EE, AA, RR] =
        fa.dimap(g, h).provideSome(f)
    }
}
