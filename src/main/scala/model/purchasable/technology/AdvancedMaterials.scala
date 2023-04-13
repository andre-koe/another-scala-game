package model.purchasable.technology

import model.game.Round
import model.purchasable.technology.ITechnology
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}


case class AdvancedMaterials(name: String = "Advanced Materials",
                             roundsToComplete: Round = Round(3),
                             cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                             description: String = "Advanced Materials") extends ITechnology:
  override def toString: String
  = "Advanced Materials"

  override def decreaseRoundsToComplete: AdvancedMaterials =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)
