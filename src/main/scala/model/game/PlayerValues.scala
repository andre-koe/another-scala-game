package model.game

import model.purchasable.IGameObject
import model.purchasable.building.IBuilding
import model.purchasable.technology.ITechnology
import model.purchasable.units.IUnit
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.game.map.system.System
import model.game.map.system.SystemType.BASE
import model.game.map.system.Affiliation.PLAYER
import model.purchasable.utils.Output


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
                        capacity: Capacity = Capacity(20),
                        systems: List[System] = List(System(affiliation = PLAYER, systemType = BASE, units = None)),
                        upkeep: ResourceHolder = ResourceHolder(descriptor = "Running Cost"),
                        income: ResourceHolder = ResourceHolder(descriptor = "Income"))

 // def decreaseRemainingRoundsForTechnologies: List[ITechnology] = listOfTechnologiesCurrentlyResearched.foreach(_.decreaseRoundsToComplete)
 // def decreaseRemainingRoundsForUnits: List[IUnit] = listOfUnitsUnderConstruction.foreach(_.decreaseRoundsToComplete)
 // def decreaseRemainingRoundsForBuildings: List[IBuilding] = listOfBuildingsUnderConstruction.foreach(_.decreaseRoundsToComplete)