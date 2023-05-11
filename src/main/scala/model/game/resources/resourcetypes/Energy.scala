package model.game.resources.resourcetypes

import model.game.resources.IResource

case class Energy(value: Int = 0, final val name: String = "Energy") extends IResource[Energy]:
  
  override def increase(other: Energy): Energy = this.copy(value = value + other.value)
  
  override def decrease(other: Energy): Option[Energy] = 
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String = s"[$name: $value]"


