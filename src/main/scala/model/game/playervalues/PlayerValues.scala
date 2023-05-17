package model.game.playervalues

import model.game.Capacity
import model.game.map.Coordinate
import model.game.map.system.Affiliation.PLAYER
import model.game.map.system.Sector
import model.game.map.system.SectorType.BASE
import model.game.purchasable.IGameObject
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}


case class PlayerValues(resourceHolder: ResourceHolder = ResourceHolder(
                          descriptor = "Balance",
                          energy = Energy(1000),
                          minerals = Minerals(1000),
                          researchPoints = ResearchPoints(1000),
                          alloys = Alloys(10)),
                        listOfBuildings: List[IBuilding] = List[IBuilding]().empty,
                        listOfUnits: List[IUnit] = List[IUnit]().empty,
                        listOfTechnologies: List[ITechnology] = List[ITechnology]().empty,
                        listOfBuildingsUnderConstruction: List[IBuilding] = List[IBuilding]().empty,
                        listOfTechnologiesCurrentlyResearched: List[ITechnology] = List[ITechnology]().empty,
                        listOfUnitsUnderConstruction: List[IUnit] = List[IUnit]().empty,
                        capacity: Capacity = Capacity(3),
                        systems: List[Sector] =
                        List(Sector(affiliation = PLAYER,
                          sectorType = BASE, units = None, coordinate = Coordinate())),
                        upkeep: ResourceHolder = ResourceHolder(descriptor = "Running Cost"),
                        income: ResourceHolder = ResourceHolder(descriptor = "Income"))