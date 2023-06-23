package model.core.mechanics

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.board.boardutils.{Coordinate, ICoordinate}
import utils.CirceImplicits._

case class MoveVector(start: ICoordinate = Coordinate(-1,-1), 
                      target: ICoordinate = Coordinate(-1,-1)) extends IMoveVector:

  def setStart(sector: ICoordinate): MoveVector = this.copy(start = sector)

  def setTarget(sector: ICoordinate): MoveVector = this.copy(target = sector)

  def getDistance: Option[Int] = if isValid then Some(start.getDistance(target)) else None

  private def isValid: Boolean = start.xPos > -1 && start.yPos > -1 && target.yPos > -1 && target.xPos > -1


object MoveVector:
  implicit val encoder: Encoder[MoveVector] = deriveEncoder[MoveVector]
  implicit val decoder: Decoder[MoveVector] = deriveDecoder[MoveVector]