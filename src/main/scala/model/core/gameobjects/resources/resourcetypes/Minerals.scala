package model.core.gameobjects.resources.resourcetypes

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.gameobjects.resources.IResource


case class Minerals(value: Int = 0, final val name: String = "Minerals") extends IResource[Minerals]:
  
  override def increase(other: Minerals): Minerals = this.copy(value = this.value + other.value)
  
  override def decrease(other: Minerals): Option[Minerals] =
    if value >= other.value then Option(this.copy(value = value - other.value)) else None
    
  override def toString: String = s"[$name: $value]"

object Minerals:
  implicit val encoder: Encoder[Minerals] = deriveEncoder[Minerals]
  implicit val decoder: Decoder[Minerals] = deriveDecoder[Minerals]

    
    
