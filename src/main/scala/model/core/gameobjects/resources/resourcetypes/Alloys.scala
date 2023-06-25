package model.core.gameobjects.resources.resourcetypes

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.gameobjects.purchasable.technology.NanoRobotics
import model.core.gameobjects.resources.IResource


case class Alloys(value: Int = 0, final val name: String = "Alloys") extends IResource[Alloys]:
  
  override def increase(other: Alloys): Alloys = this.copy(value = value + other.value)
  
  override def decrease(other: Alloys): Option[Alloys] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String = s"[$name: $value]"

object Alloys:
  implicit val encoder: Encoder[Alloys] = deriveEncoder[Alloys]
  implicit val decoder: Decoder[Alloys] = deriveDecoder[Alloys]
