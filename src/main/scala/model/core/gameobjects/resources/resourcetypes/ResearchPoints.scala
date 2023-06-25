package model.core.gameobjects.resources.resourcetypes

import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import io.circe.{Decoder, Encoder}
import model.core.gameobjects.purchasable.technology.NanoRobotics
import model.core.gameobjects.resources.IResource


case class ResearchPoints(value: Int = 0, final val name: String = "Research Points") extends IResource[ResearchPoints]:
  
  override def increase(other: ResearchPoints): ResearchPoints = this.copy(value = value + other.value)
  
  override def decrease(other: ResearchPoints): Option[ResearchPoints] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String =s"[$name: $value]"

object ResearchPoints:
  implicit val encoder: Encoder[ResearchPoints] = deriveEncoder[ResearchPoints]
  implicit val decoder: Decoder[ResearchPoints] = deriveDecoder[ResearchPoints]


