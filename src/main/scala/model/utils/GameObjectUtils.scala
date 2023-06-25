package model.utils

import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.technology.{ITechnology, TechnologyFactory}
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{GameValues, IGameValues}
import model.core.utilities.interfaces.{IPurchasable, IRoundBasedConstructable}

class GameObjectUtils(gameValues: IGameValues = GameValues()):

  def findInLists(str: String): Option[IGameObject & IRoundBasedConstructable & IPurchasable] =
    val technology = findInTech(str.toLowerCase)
    val building = findInBuildings(str.toLowerCase)
    val unit = findInUnits(str.toLowerCase)
    technology.orElse(building).orElse(unit).orElse(None)
    
  private def findInTech(str: String): Option[ITechnology] =
    gameValues.tech.find(_.name.toLowerCase == str)

  private def findInBuildings(str: String): Option[IBuilding] =
    gameValues.buildings.find(_.name.toLowerCase == str)
    
  private def findInUnits(str: String): Option[IUnit] =
    gameValues.units.find(_.name.toLowerCase == str)