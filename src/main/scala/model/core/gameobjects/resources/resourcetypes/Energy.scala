package model.core.gameobjects.resources.resourcetypes

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.gameobjects.purchasable.technology.NanoRobotics
import model.core.gameobjects.resources.IResource


case class Energy(value: Int = 0, final val name: String = "Energy") extends IResource[Energy]:

  override def increase(other: Energy): Energy = this.copy(value = value + other.value)
  
  override def decrease(other: Energy): Option[Energy] = 
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String = s"[$name: $value]"

object Energy:
  implicit val encoder: Encoder[Energy] = deriveEncoder[Energy]
  implicit val decoder: Decoder[Energy] = deriveDecoder[Energy]


