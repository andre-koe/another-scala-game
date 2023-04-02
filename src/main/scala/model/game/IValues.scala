package model.game

import model.purchasable.building.Building
import model.purchasable.technology.Technology
import model.purchasable.units.Ship

trait IValues:
  def listOfBuildings: List[Building]
  def listOfTechnologies: List[Technology]
  def listOfUnits: List[Ship]
