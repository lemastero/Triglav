package Triglav.catpro

object ProReader {
  type ProReader[A,B,X,Y] = (X => A, B => Y)
}
