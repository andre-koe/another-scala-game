package model.core.mechanics

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import model.core.board.boardutils.{Coordinate, ICoordinate}
import utils.CirceImplicits._

case class MoveVector(start: ICoordinate = Coordinate(-1,-1), target: ICoordinate = Coordinate(-1,-1)) extends IMoveVector:

  override def setStart(sector: ICoordinate): MoveVector = this.copy(start = sector)

  override def setTarget(sector: ICoordinate): MoveVector = this.copy(target = sector)

  override def getDistance: Option[Int] = if isValid then Some(start.getDistance(target)) else None

  override def nextStep: MoveVector =
    if (start == target) return this

    val dx = target.xPos - start.xPos
    val dy = target.yPos - start.yPos

    val stepX = if (dx != 0) dx / math.abs(dx) else 0
    val stepY = if (dy != 0) dy / math.abs(dy) else 0

    this.copy(start = Coordinate(start.xPos + stepX, start.yPos + stepY))

  override def isMoving: Boolean = if isValid then start.yPos != target.yPos || start.xPos != target.xPos else false

  private def isValid: Boolean = start.xPos > -1 && start.yPos > -1 && target.yPos > -1 && target.xPos > -1

object MoveVector:
  implicit val encoder: Encoder[MoveVector] = deriveEncoder[MoveVector]
  implicit val decoder: Decoder[MoveVector] = deriveDecoder[MoveVector]