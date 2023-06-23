package model.core.board.boardutils

import model.core.board.sector.ISector
import model.core.board.sector.impl.PlayerSector
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.mechanics.fleets.components.units.IUnit
import model.core.mechanics.fleets.IFleet
import model.core.utilities.{BuildSlots, Capacity}

trait IGameBoardInfoWrapper:

  def getData: Vector[ISector]

  def update(sector: ISector): GameBoardInfoWrapper

  def getSector(row: Int, col: Int): Option[ISector]

  def getPlayerSectorCount: Int

  def getFreeBuildSlots: Int

  def getUsedCapacity: Int

  def getFreeBuildSlotsInSectorTotal(sector: ISector): Int

  def getFreeBuildSlotsInSectorRemaining(sector: ISector): Option[Int]

  def getBuildingsInSector(sector: ISector): Vector[IBuilding]

  def getBuildingConstructionInSector(sector: ISector): Vector[IBuilding]

  def getUnitsInSector(sector: ISector): Vector[IUnit]

  def getUnitConstructionInSector(sector: ISector): Vector[IUnit]

  def getFleetsInSector(sector: ISector): Vector[IFleet]

  def getBuildingsInSectors: Vector[IBuilding]

  def getFleetsInSectors: Vector[IFleet]

  def getUnitsInSectors: Vector[IUnit]

  def getUnitConstructionInSectors: Vector[IUnit]

  def getBuildingConstructionInSectors: Vector[IBuilding]
  
  def getSizeX: Int
  
  def getSizeY: Int



