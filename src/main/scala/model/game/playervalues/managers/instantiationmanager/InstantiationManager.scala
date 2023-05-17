package model.game.playervalues.managers.instantiationmanager

import model.game.purchasable.IGameObject
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit

abstract class InstantiationManager[T <: IGameObject]:

  def addToProduction(rec: T): InstantiationManager[T]

  def addToInventory(rec: T): InstantiationManager[T]