package model.core.utilities

trait IBuildSlots:

  def value: Int
  
  def increase(other: IBuildSlots): IBuildSlots

  def decrease(other: IBuildSlots): Option[IBuildSlots]
  def decrement: Option[IBuildSlots]