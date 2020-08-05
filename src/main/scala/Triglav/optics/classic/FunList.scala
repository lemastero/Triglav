package Triglav.optics.classic

sealed trait FunList[+A,-B,+T]
case class Done[+A,-B,+T](v: T)
  extends FunList[A,B,T]
case class More[+A,-B,+T](h: A, t: FunList[A,B,B=>T])
  extends FunList[A,B,T]
