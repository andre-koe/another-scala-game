package model.game

import model.purchasable.building.{Building, EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer, Technology}
import model.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, Ship}

case class GameValues(listOfBuildings: List[Building]
                       = List[Building](ResearchLab(), EnergyGrid(), Shipyard(), Hangar(), Factory(), Mine()),
                      listOfUnits: List[Ship]
                       = List[Ship](Corvette(), Cruiser(), Destroyer(), Battleship()),
                      listOfTechnologies: List[Technology]
                       = List[Technology](Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion())) extends IValues

