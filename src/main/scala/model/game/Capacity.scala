package model.game

import model.utils.Increaseable
import scala.annotation.tailrec

case class Capacity(value: Int = 0) extends Increaseable[Capacity]:
  
  override def increase(other: Capacity): Capacity = this.copy(value = this.value + other.value)

  def decrease(other: Capacity): Option[Capacity] =
    if value >= other.value then Option(this.copy(value = this.value - other.value)) else None

  def lacking(other: Capacity): Capacity = this.copy(value = other.value - this.value)

  def multiplyBy(multiplier: Int): Capacity = this.copy(value = this.value * multiplier)

  def holds(other: Capacity): Option[Int] =
    val fraction = this.value / other.value
    if fraction >= 1 then Option(fraction) else None
  override def toString: String = s"[Capacity: $value]"
