package model.resources.resourcetypes

import model.resources.IResource

case class ResearchPoints(value: Int = 0, final val name: String = "Research Points") extends IResource[ResearchPoints]:
  override def increase(other: ResearchPoints): ResearchPoints = this.copy(value = value + other.value)
  override def decrease(other: ResearchPoints): Option[ResearchPoints] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
  override def toString: String =s"[$name: $value]"


