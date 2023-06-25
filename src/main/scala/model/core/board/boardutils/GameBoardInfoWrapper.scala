package model.core.board.boardutils

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.{GameBoard, IGameBoard}
import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector}
import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.mechanics.fleets.IFleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{BuildSlots, Capacity, IBuildSlots, ICapacity}

case class GameBoardInfoWrapper(gameBoard: IGameBoard) extends IGameBoardInfoWrapper:

  override def getData: Vector[ISector] = gameBoard.data.flatten

  override def update(sector: ISector): GameBoardInfoWrapper = this.copy(gameBoard.updateSector(sector))

  override def getSector(row: Int, col: Int): Option[ISector] =
    if row < gameBoard.sizeY && col < gameBoard.sizeX then Some(gameBoard.data(row)(col)) else None

  override def getPlayerSectorCount(affiliation: Affiliation): Int = gameBoard.getPlayerSectors(affiliation).length

  override def getFreeBuildSlots(affiliation: Affiliation): Int =
    gameBoard
      .getPlayerSectors(affiliation)
      .map(_.buildSlots)
      .foldLeft(BuildSlots())((x: IBuildSlots,y: IBuildSlots) => x.increase(y)).value

  override def getUsedCapacity(affiliation: Affiliation): Int =
    val fromUnits = gameBoard.getPlayerSectors(affiliation).flatMap(_.unitsInSector).map(_.capacity)
      .foldLeft(Capacity())((x: ICapacity, y: ICapacity) => x.increase(y))
    val fromConstruction = getUnitConstructionInSectors(affiliation).map(_.capacity)
      .foldLeft(Capacity())((x: ICapacity, y:ICapacity) => x.increase(y))

    fromUnits.increase(fromConstruction).value

  override def getFreeBuildSlotsInSectorTotal(sector: ISector): Int = sector.buildSlots.value

  override def getFreeBuildSlotsInSectorRemaining(sector: ISector): Option[Int] =
    sector match
      case x: PlayerSector => Some(x.buildSlots.value - (x.buildingsInSector.length + x.constQuBuilding.length))
      case _ => None

  override def getBuildingsInSector(sector: ISector): Vector[IBuilding] =
    sector match
      case x: PlayerSector => x.buildingsInSector
      case _ => Vector()

  override def getBuildingConstructionInSector(sector: ISector): Vector[IBuilding] =
    sector match
      case x: PlayerSector => x.constQuBuilding
      case _ => Vector()

  override def getFleetsInSector(sector: ISector): Vector[IFleet] = sector.unitsInSector

  override def getUnitsInSector(sector: ISector): Vector[IUnit] = sector.unitsInSector.flatMap(_.units)

  override def getUnitConstructionInSector(sector: ISector): Vector[IUnit] =
    sector match
      case x: PlayerSector => x.constQuUnits
      case _ => Vector()

  override def getBuildingsInSectors(affiliation: Affiliation): Vector[IBuilding] =
    gameBoard.getPlayerSectors(affiliation).flatMap(_.buildingsInSector)

  override def getFleetsInSectors(affiliation: Affiliation): Vector[IFleet] =
    gameBoard.getPlayerSectors(affiliation).flatMap(_.unitsInSector)

  override def getUnitsInSectors(affiliation: Affiliation): Vector[IUnit] =
    gameBoard.getSectors.flatMap(_.unitsInSector).filter(_.affiliation == affiliation).flatMap(_.units)

  override def getUnitConstructionInSectors(affiliation: Affiliation): Vector[IUnit] =
    gameBoard.getPlayerSectors(affiliation).flatMap(_.constQuUnits)

  override def getBuildingConstructionInSectors(affiliation: Affiliation): Vector[IBuilding] =
    gameBoard.getPlayerSectors(affiliation).flatMap(_.constQuBuilding)

  override def getSizeX: Int = gameBoard.sizeX

  override def getSizeY: Int = gameBoard.sizeY

