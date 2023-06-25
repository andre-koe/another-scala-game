package model.core.board

import io.circe.*
import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.sectorutils.Affiliation
import model.core.mechanics.fleets.IFleet
import utils.IXMLSerializable

trait IGameBoard extends IXMLSerializable:

  def sizeX: Int

  def sizeY: Int

  def data: Vector[Vector[ISector]]

  def toPlayerSector(coordinate: ICoordinate, fleet: IFleet): IGameBoard

  def updateSector(sector: ISector): IGameBoard

  def update: IGameBoard

  def getSectorAtCoordinate(coordinate: ICoordinate): Option[ISector]

  def getPlayerSectors(affiliation: Affiliation): Vector[IPlayerSector]

  def getSectors: Vector[ISector]

  def sectorExists(coordinate: ICoordinate): Boolean

  def toXML: scala.xml.Elem =
    val xmlData = data.flatMap(_.map(_.toXML))
    <GameBoard>
      <SizeX>{sizeX}</SizeX>
      <SizeY>{sizeY}</SizeY>
      <Data>{xmlData}</Data>
    </GameBoard>

