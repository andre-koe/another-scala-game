package model.utils

import model.game.purchasable.{IGameObject, IPurchasableFactory}
import model.game.purchasable.building.{BuildingFactory, IBuilding}
import model.game.purchasable.technology.{ITechnology, TechnologyFactory}
import model.game.purchasable.units.{IUnit, UnitFactory}

class GameObjectUtils():

  def findInLists(str: String): Option[IGameObject] =
    val technology = findInTech(str)
    val building = findInBuildings(str)
    val unit = findInUnits(str)
    technology.orElse(building).orElse(unit).orElse(None)
    
  private def findInTech(str: String): Option[ITechnology] = existsInFactory(str, () => TechnologyFactory())

  private def findInBuildings(str: String): Option[IBuilding] = existsInFactory(str, () => BuildingFactory())
    
  private def findInUnits(str: String): Option[IUnit] = existsInFactory(str, () => UnitFactory())

  private def existsInFactory[T <: IGameObject](name: String, factory: () => IPurchasableFactory[T]): Option[T] =
    factory().create(name.toLowerCase)
