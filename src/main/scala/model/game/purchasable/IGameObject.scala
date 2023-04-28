package model.game.purchasable

import model.game.Round
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.Energy

trait IGameObject:
  def name: String
  def description: String
  def cost: ResourceHolder
  def roundsToComplete: Round
  def decreaseRoundsToComplete: IGameObject

