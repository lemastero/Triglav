package Triglav.wip

object ProReader {
  type ProReader[A,B,X,Y] = (X => A, B => Y)
}
