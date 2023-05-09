package model.game.purchasable.building

import model.game.Round
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}

case class Factory(name: String = "Factory",
                   roundsToComplete: Round = Round(6),
                   cost: ResourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(75)),
                   description: String = "The Factory processes minerals into alloys " +
                     "which are needed for construction of buildings and ships.",
                   upkeep: ResourceHolder = ResourceHolder(energy = Energy(5), minerals = Minerals(10)),
                   output: Output = Output(rHolder = ResourceHolder(alloys = Alloys(5)))) extends IBuilding:
  
  override def toString: String = "Factory"

  override def decreaseRoundsToComplete: Factory =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)

