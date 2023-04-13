package model.purchasable.building

import model.game.Round
import model.purchasable.utils.Output
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
case class ResearchLab(name: String = "Research Lab",
                       roundsToComplete: Round = Round(3),
                       cost: ResourceHolder = ResourceHolder(
                         energy = Energy(100),
                         minerals = Minerals(100),
                         alloys = Alloys(100)),
                       description: String = "The Research Lab increases research output.",
                       upkeep: ResourceHolder = ResourceHolder(energy = Energy(10), alloys = Alloys(10)),
                       output: Output = Output(ResourceHolder(researchPoints = ResearchPoints(20)))
                      ) extends IBuilding:
    override def toString: String = "Research Lab"

    override def decreaseRoundsToComplete: ResearchLab =
      this.copy(roundsToComplete = this.roundsToComplete.decrease.get)
