package Triglav.face0

// taken from ZIO: https://github.com/zio/zio/pull/45/files/a3d4103f9fd6b13e84adfec775f105884ae77423#diff-04c6e90faac2675aa89e2176d2eec7d8
abstract final class Void {
  def absurd[A]: A
}
