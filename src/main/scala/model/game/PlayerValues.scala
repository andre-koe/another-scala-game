package model.game

import model.purchasable.IGameObject
import model.purchasable.building.IBuilding
import model.purchasable.technology.ITechnology
import model.purchasable.units.IUnit
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}

case class PlayerValues(resourceHolder: ResourceHolder = ResourceHolder(
                          descriptor = "Balance",
                          energy = Energy(100),
                          minerals = Minerals(100),
                          researchPoints = ResearchPoints(100),
                          alloys = Alloys(10)),
                        listOfBuildings: List[IBuilding] = List[IBuilding]().empty,
                        listOfUnits: List[IUnit] = List[IUnit]().empty,
                        listOfTechnologies: List[ITechnology] = List[ITechnology]().empty,
                        listOfBuildingsUnderConstruction: List[IBuilding] = List[IBuilding]().empty,
                        listOfTechnologiesCurrentlyResearched: List[ITechnology] = List[ITechnology]().empty,
                        listOfUnitsUnderConstruction: List[IUnit] = List[IUnit]().empty,
                        capacity: Capacity = Capacity(20),
                        upkeep: ResourceHolder = ResourceHolder(descriptor = "Running Cost")
                       )

 // def decreaseRemainingRoundsForTechnologies: List[ITechnology] = listOfTechnologiesCurrentlyResearched.foreach(_.decreaseRoundsToComplete)
 // def decreaseRemainingRoundsForUnits: List[IUnit] = listOfUnitsUnderConstruction.foreach(_.decreaseRoundsToComplete)
 // def decreaseRemainingRoundsForBuildings: List[IBuilding] = listOfBuildingsUnderConstruction.foreach(_.decreaseRoundsToComplete)