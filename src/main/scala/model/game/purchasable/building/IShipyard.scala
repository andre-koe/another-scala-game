package model.game.purchasable.building

import model.game.purchasable.units.IUnit

trait IShipyard extends IBuilding:

  def createUnit(): IUnit