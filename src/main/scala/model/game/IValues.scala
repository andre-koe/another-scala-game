package model.game

import model.purchasable.building.IBuilding
import model.purchasable.technology.ITechnology
import model.purchasable.units.IUnit

trait IValues:
  def listOfBuildings: List[IBuilding]
  def listOfTechnologies: List[ITechnology]
  def listOfUnits: List[IUnit]
