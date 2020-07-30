package Triglav.wip

trait ~[A,B] {
  def to: B => A
  def from: A => B
}
