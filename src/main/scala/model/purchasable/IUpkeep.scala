package model.purchasable

import model.resources.ResourceHolder

trait IUpkeep extends IGameObject:
  
  def upkeep: ResourceHolder
