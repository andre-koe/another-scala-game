package model.game.purchasable.technology

import model.game.Round
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}


case class AdvancedPropulsion(name: String = "Advanced Propulsion", 
                              roundsToComplete: Round = Round(3),
                              cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                              description: String = "Advanced Propulsion") extends ITechnology:
  override def toString: String
  = "Advanced Propulsion"

  override def decreaseRoundsToComplete: AdvancedPropulsion =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)