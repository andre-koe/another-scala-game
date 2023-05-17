package model.core.sector.decorator

import model.core.sector.ISector
import model.core.sector.Sector
import model.game.map.system.SectorType
import model.game.map.Coordinate
import model.game.map.system.Affiliation
import model.game.purchasable.building.IBuilding
import model.game.purchasable.units.IUnit
import model.core.utilities.ListOperations
import model.game.BuildSlots
import model.game.playervalues.managers.fleetmanager.fleet.Fleet

case class PlayerSector(sector: ISector,
                        buildingsInProduction: List[IBuilding],
                        buildingsInSector: List[IBuilding],
                        unitsInSector: List[IUnit],
                        buildingSlots: BuildSlots) extends SectorDecorator:

  override def switchAffiliation(affiliation: Affiliation): ISector =
    affiliation match
      case Affiliation.PLAYER => this
      case _ => Sector(location, affiliation, sectorType)
  
  def addBuilding(str: String): PlayerSector = ???

  def addUnits(fleet: Fleet | IUnit): PlayerSector = ???

  def updateBuildings(): PlayerSector =
    val (finished, stillInProduction) = ListOperations().handleList(buildingsInProduction)
    this.copy(buildingsInSector = buildingsInSector ++ finished, buildingsInProduction = stillInProduction)


