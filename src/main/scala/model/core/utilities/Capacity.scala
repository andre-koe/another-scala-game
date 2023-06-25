package model.core.utilities

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.utils.Increaseable

import scala.annotation.tailrec


case class Capacity(value: Int = 0) extends ICapacity:
  
  override def increase(other: ICapacity): ICapacity = this.copy(value = this.value + other.value)

  def decrease(other: ICapacity): Option[ICapacity] =
    if value >= other.value then Option(this.copy(value = this.value - other.value)) else None

  def lacking(other: ICapacity): ICapacity = this.copy(value = other.value - this.value)

  def multiplyBy(multiplier: Int): ICapacity = this.copy(value = this.value * multiplier)

  def holds(other: ICapacity): Option[Int] =
    val fraction = this.value / other.value
    if fraction >= 1 then Option(fraction) else None

  override def empty(): ICapacity = Capacity()
  
  override def toString: String = s"[Capacity: $value]"
