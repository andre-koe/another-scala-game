package model.game.purchasable.technology

import model.game.Round
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}


case class AdvancedMaterials(name: String = "Advanced Materials",
                             roundsToComplete: Round = Round(3),
                             cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                             description: String = "Advanced Materials") extends ITechnology:
  override def toString: String
  = "Advanced Materials"

  override def decreaseRoundsToComplete: AdvancedMaterials =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)
