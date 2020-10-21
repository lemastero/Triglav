package Triglav.instances

import Triglav.face3.Fnfunctor
import zio.Schedule

object NiFunctorInstancesZIO {

  // zio.Schedule[-Env, -In, +Out]

  implicit val ScheduleNifunctor: Fnfunctor[Schedule] =
    new Fnfunctor[Schedule] {
      override def fnmap[E, A, R, EE, AA, RR](
          fa: Schedule[E, A, R]
      )(f: EE => E, g: AA => A, h: R => RR): Schedule[EE, AA, RR] =
        fa.dimap(g, h).provideSome(f)
    }
}
