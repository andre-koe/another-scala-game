package model.game.playervalues.managers.instantiationmanager

import model.game.purchasable.IGameObject
import model.game.purchasable.units.IUnit

case class RecruitmentManager(unitsInInventory: List[IUnit] = List[IUnit]().empty,
                              unitsInProduction: List[IUnit] = List[IUnit]().empty) extends InstantiationManager[IUnit]:


  override def addToProduction(rec: IUnit): RecruitmentManager = this.copy(unitsInProduction = unitsInProduction.+:(rec))

  override def addToInventory(rec: IUnit): RecruitmentManager = this.copy(unitsInInventory = unitsInInventory.+:(rec))




