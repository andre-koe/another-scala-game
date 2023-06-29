package model.core.board

import io.circe.*
import io.circe.generic.semiauto.*
import io.circe.syntax.EncoderOps
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector}
import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.resources.resourcetypes.Energy
import model.core.mechanics.fleets.IFleet
import model.core.mechanics.fleets.components.units.IUnit
import utils.CirceImplicits.*

case class GameBoard(sizeX: Int, sizeY: Int, data: Vector[Vector[ISector]]) extends IGameBoard:

  def toPlayerSector(coordinate: ICoordinate, fleet: IFleet): IGameBoard =
    getSectorAtCoordinate(coordinate).map { sector =>
      val newSector = PlayerSector(sector = sector.cloneWith(unitsInSector = Vector(fleet), affiliation = fleet.affiliation))
      val updatedY = data(coordinate.xPos).updated(coordinate.yPos, newSector)
      val updatedM = data.updated(coordinate.xPos, updatedY)
      this.copy(data = updatedM)
    }.getOrElse(this)
    
  def updateSector(sector: ISector): IGameBoard =
    val updatedY = data(sector.location.xPos).updated(sector.location.yPos, sector)
    val updatedM = data.updated(sector.location.xPos, updatedY)
    this.copy(data = updatedM)

  def update: IGameBoard = this.copy(data = data.map(
    x => x.map {
        case t: IPlayerSector => t.updateSector();
        case e => e
      })
  )

  def getSectorAtCoordinate(coordinate: ICoordinate): Option[ISector] =
    if sectorExists(coordinate) then Some(data(coordinate.xPos)(coordinate.yPos)) else None

  def getPlayerSectors(affiliation: Affiliation): Vector[IPlayerSector] =
    data.flatten.filter(_.isInstanceOf[PlayerSector]).filter(_.affiliation == affiliation).map(_.asInstanceOf[PlayerSector])

  def getSectors: Vector[ISector] = data.flatten

  def sectorExists(coordinate: ICoordinate): Boolean =
    val x = coordinate.xPos
    val y = coordinate.yPos
    x >= 0 && x < sizeX && y >= 0 && y < sizeY

  private def separator: String = "\n" + ("  | " + " " * 4) * sizeX + "\n"

  private def limiter: String = "=" * ("(1-1)" * sizeX + "===" * (sizeX - 1)).length

  override def toString: String = limiter + "\n" + data.map(_.mkString("-" * 3)).mkString(separator) + "\n" + limiter

object GameBoard:
  implicit val encoder: Encoder[GameBoard] = deriveEncoder[GameBoard]
  implicit val decoder: Decoder[GameBoard] = deriveDecoder[GameBoard]
