package model.purchasable.units

import model.game.{Capacity, Round}
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, Minerals}

case class Destroyer(name: String = "Destroyer",
                     roundsToComplete: Round = Round(3),
                     cost: ResourceHolder = ResourceHolder(energy = Energy(130), minerals = Minerals(90)),
                     description: String = "Some description for Unit Destroyer",
                     upkeep: ResourceHolder = ResourceHolder(energy = Energy(8)),
                     capacity: Capacity = Capacity(2),
                     attack: Int = 100,
                     defense: Int = 40) extends IUnit:
  override def toString: String = "Destroyer"
  override def decreaseRoundsToComplete: Destroyer =
    this.copy(roundsToComplete = roundsToComplete.decrease.get)