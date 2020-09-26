package Triglav.face2

import zio.test.Assertion._
import zio.test.{DefaultRunnableSpec, Gen, assert, check, suite, testM}

object HelloSpec extends DefaultRunnableSpec {

  def spec = suite("addition")(
    testM("associativyty") {
      check(Gen.anyInt, Gen.anyInt, Gen.anyInt) { (x, y, z) =>
        assert((x + y) + z)(equalTo(x + (y + z)))
      }
    }
  )
}
