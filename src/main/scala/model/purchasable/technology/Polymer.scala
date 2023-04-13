package model.purchasable.technology

import model.game.Round
import model.purchasable.technology.ITechnology
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

case class Polymer(name: String = "Polymer",
                   roundsToComplete: Round = Round(3),
                   cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                   description: String = "Polymer") extends ITechnology:
  override def toString: String = "Polymer"

  override def decreaseRoundsToComplete: Polymer =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)