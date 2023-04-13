package model.purchasable.technology

import model.game.Round
import model.purchasable.technology.ITechnology
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}


case class AdvancedPropulsion(name: String = "Advanced Propulsion", 
                              roundsToComplete: Round = Round(3),
                              cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                              description: String = "Advanced Propulsion") extends ITechnology:
  override def toString: String
  = "Advanced Propulsion"

  override def decreaseRoundsToComplete: AdvancedPropulsion =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)