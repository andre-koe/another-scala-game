package model.game

import model.purchasable.building.Building
import model.purchasable.technology.Technology
import model.purchasable.units.Ship

case class PlayerValues(listOfBuildings: List[Building] = List[Building]().empty,
                        listOfUnits: List[Ship] = List[Ship]().empty,
                        listOfTechnologies: List[Technology] = List[Technology]().empty) extends IValues
