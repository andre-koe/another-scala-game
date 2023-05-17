package model.game.purchasable.units

import model.game.Capacity
import model.game.map.Coordinate
import model.game.purchasable.{IGameObject, IUpkeep}

trait IUnit extends IUpkeep:

  def location: Coordinate

  def speed: Int

  def firepower: Int

  def capacity: Capacity

  def move(target: Coordinate): IUnit


