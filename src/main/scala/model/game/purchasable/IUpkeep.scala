package model.game.purchasable

import model.game.resources.ResourceHolder

trait IUpkeep extends IGameObject:
  
  def upkeep: ResourceHolder
