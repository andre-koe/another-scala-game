package model.purchasable.technology

import model.game.Round
import model.purchasable.technology.ITechnology
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}



case class NanoRobotics(name: String = "Nano Robotics",
                        roundsToComplete: Round = Round(3),
                        cost: ResourceHolder = ResourceHolder(researchPoints = ResearchPoints(200)),
                        description: String = "Nano Robotics") extends ITechnology:
  override def toString: String
  = "Nano Robotics"

  override def decreaseRoundsToComplete: NanoRobotics =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)
