package model.core.board.boardutils

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import utils.CirceImplicits._
import io.circe.syntax.*

case class Coordinate(xPos: Int, yPos: Int) extends ICoordinate:

  def getDistance(other: ICoordinate): Int = Math.abs(xPos - other.xPos) + Math.abs(yPos - other.yPos)

  override def toString: String = s"$xPos-$yPos"


object Coordinate:
  implicit val encoder: Encoder[Coordinate] = deriveEncoder[Coordinate]
  implicit val decoder: Decoder[Coordinate] = deriveDecoder[Coordinate]