package model.game.purchasable.building

import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, Minerals}
import model.game.{Capacity, Round}

case class Hangar(name: String = "Hangar",
                  roundsToComplete: Round = Round(3),
                  cost: ResourceHolder = ResourceHolder(energy = Energy(50), minerals = Minerals(75)),
                  description: String = "The Hangar provides additional unit capacity.",
                  upkeep: ResourceHolder = ResourceHolder(energy = Energy(3), minerals = Minerals(5)),
                  output: Output = Output(capacity = Capacity(10))) extends IBuilding:
  override def toString: String = "Hangar"
  
  override def decreaseRoundsToComplete: Hangar =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)
    