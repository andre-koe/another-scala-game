package model.game

import model.game.purchasable.building.{EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.game.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer}
import model.game.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, IUnit}

case class GameValues(listOfBuildings: List[IBuilding]
                       = List[IBuilding](ResearchLab(), EnergyGrid(), Shipyard(), Hangar(), Factory(), Mine()),
                      listOfUnits: List[IUnit]
                       = List[IUnit](Corvette(), Cruiser(), Destroyer(), Battleship()),
                      listOfTechnologies: List[ITechnology]
                       = List[ITechnology](Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion()))

