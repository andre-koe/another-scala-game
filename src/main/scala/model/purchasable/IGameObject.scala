package model.purchasable

import model.game.Round
import model.resources.ResourceHolder
import model.resources.resourcetypes.Energy

trait IGameObject:
  def name: String
  def description: String
  def cost: ResourceHolder
  def roundsToComplete: Round
  def decreaseRoundsToComplete: IGameObject

