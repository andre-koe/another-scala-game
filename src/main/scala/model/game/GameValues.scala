package model.game

import model.purchasable.building.{IBuilding, EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer, ITechnology}
import model.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, IUnit}

case class GameValues(listOfBuildings: List[IBuilding]
                       = List[IBuilding](ResearchLab(), EnergyGrid(), Shipyard(), Hangar(), Factory(), Mine()),
                      listOfUnits: List[IUnit]
                       = List[IUnit](Corvette(), Cruiser(), Destroyer(), Battleship()),
                      listOfTechnologies: List[ITechnology]
                       = List[ITechnology](Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion())) extends IValues

