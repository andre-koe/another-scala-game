package model.game.playervalues.managers.fleetmanager.fleet

import model.game.purchasable.units.IUnit

trait IFleet[T<:IUnit] extends IUnit:

  def merge(f: T): T

  def split(): (T, T)






