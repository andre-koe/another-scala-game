package model.game.playervalues.managers.instantiationmanager

import model.game.purchasable.building.IBuilding
import model.game.purchasable.units.IUnit

case class ConstructionManager(construction: List[IBuilding],
                               buildings: List[IBuilding]) extends InstantiationManager[IBuilding]:

  override def addToProduction(rec: IBuilding): InstantiationManager[IBuilding] = this.copy(construction.+:(rec))

  override def addToInventory(rec: IBuilding): InstantiationManager[IBuilding] = this.copy(buildings.+:(rec))
