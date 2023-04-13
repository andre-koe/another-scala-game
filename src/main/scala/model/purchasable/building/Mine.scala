package model.purchasable.building

import model.game.Round
import model.purchasable.building.IBuilding
import model.purchasable.utils.Output
import model.resources.ResourceHolder
import model.resources.resourcetypes.Minerals
import model.resources.resourcetypes.Energy

case class Mine(name: String = "Mine",
                roundsToComplete: Round = Round(3),
                cost: ResourceHolder = ResourceHolder(energy = Energy(80)),
                description: String = "The Mine extracts minerals which are used for " +
                  "the construction of units and buildings. " +
                  "Minerals are the base resource for the production of alloys.",
                upkeep: ResourceHolder = ResourceHolder(energy = Energy(10)),
                output: Output = Output(resourceHolder = ResourceHolder(minerals = Minerals(10)))
               ) extends IBuilding:
  override def toString: String = "Mine"

  override def decreaseRoundsToComplete: Mine =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)