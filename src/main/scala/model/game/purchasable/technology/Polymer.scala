package model.game.purchasable.technology

import model.game.Round
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}

case class Polymer(name: String = "Polymer",
                   roundsToComplete: Round = Round(3),
                   cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                   description: String = "Polymer") extends ITechnology:
  override def toString: String = "Polymer"

  override def decreaseRoundsToComplete: Polymer =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)