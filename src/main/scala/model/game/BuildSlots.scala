package model.game

import model.utils.Increaseable

case class BuildSlots(value: Int = 0) extends Increaseable[BuildSlots]:

  override def increase(other: BuildSlots): BuildSlots = this.copy(value = this.value + other.value)

  def decrease(other: BuildSlots): Option[BuildSlots] =
    if value >= other.value then Option(this.copy(value = this.value - other.value)) else None

  def decrement: Option[BuildSlots] =
    if value - 1 >= 0 then Option(this.copy(value = this.value - 1)) else None

  override def toString: String = s"[Build Slots: $value]"
