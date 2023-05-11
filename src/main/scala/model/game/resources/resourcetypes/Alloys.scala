package model.game.resources.resourcetypes

import model.game.resources.IResource

case class Alloys(value: Int = 0, final val name: String = "Alloys") extends IResource[Alloys]:
  
  override def increase(other: Alloys): Alloys = this.copy(value = value + other.value)
  
  override def decrease(other: Alloys): Option[Alloys] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String = s"[$name: $value]"
