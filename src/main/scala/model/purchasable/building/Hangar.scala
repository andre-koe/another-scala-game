package model.purchasable.building
import model.game.{Capacity, Round}
import model.purchasable.utils.Output
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, Minerals}

case class Hangar(name: String = "Hangar",
                  roundsToComplete: Round = Round(3),
                  cost: ResourceHolder = ResourceHolder(energy = Energy(50), minerals = Minerals(75)),
                  description: String = "The Hangar provides additional unit capacity.",
                  upkeep: ResourceHolder = ResourceHolder(energy = Energy(3), minerals = Minerals(5)),
                  output: Output = Output(capacity = Capacity(10))) extends IBuilding:
  override def toString: String = "Hangar"
  
  override def decreaseRoundsToComplete: Hangar =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)
    