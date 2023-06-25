package model.core.utilities

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.utils.Increaseable

case class BuildSlots(value: Int = 0) extends IBuildSlots:

  override def increase(other: IBuildSlots): IBuildSlots = this.copy(value = this.value + other.value)

  def decrease(other: IBuildSlots): Option[IBuildSlots] =
    if value >= other.value then Option(this.copy(value = this.value - other.value)) else None

  def decrement: Option[IBuildSlots] =
    if value - 1 >= 0 then Option(this.copy(value = this.value - 1)) else None

  override def toString: String = s"[Build Slots: $value]"
