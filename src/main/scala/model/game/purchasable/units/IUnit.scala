package model.game.purchasable.units

import model.game.Capacity
import model.game.purchasable.{IGameObject, IUpkeep}

trait IUnit extends IUpkeep:
  
  def attack: Int
  def defense: Int
  def capacity: Capacity
