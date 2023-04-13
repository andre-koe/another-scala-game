package model.game

case class Capacity(value: Int = 0):
  
  def increase(other: Capacity): Capacity = this.copy(value = this.value + other.value)
  def decrease(other: Capacity): Option[Capacity] =
    if value >= other.value then Option(this.copy(value = this.value - other.value)) else None
  override def toString: String = s"[Capacity: $value]"
