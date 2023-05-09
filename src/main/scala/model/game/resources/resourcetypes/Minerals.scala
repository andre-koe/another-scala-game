package model.game.resources.resourcetypes

import model.game.resources.IResource

case class Minerals(value: Int = 0, final val name: String = "Minerals") extends IResource[Minerals]:
  
  override def increase(other: Minerals): Minerals = this.copy(value = this.value + other.value)
  
  override def decrease(other: Minerals): Option[Minerals] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String = s"[$name: $value]"

    
    
