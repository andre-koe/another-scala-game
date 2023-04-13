package model.purchasable.building
import model.game.Round
import model.purchasable.IGameObject
import model.purchasable.utils.Output
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, Minerals}

case class EnergyGrid(name: String = "Energy Grid",
                      roundsToComplete: Round = Round(6),
                      cost: ResourceHolder = ResourceHolder(minerals = Minerals(75)),
                      description: String = "The Energy Grid " +
                        "provides a steady stream of energy to power buildings and units.",
                      upkeep: ResourceHolder = ResourceHolder(energy = Energy(3)),
                      output: Output = Output(resourceHolder = ResourceHolder(energy = Energy(10)))
                     ) extends IBuilding:
  
  override def toString: String = "Energy Grid"

  override def decreaseRoundsToComplete: EnergyGrid = 
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)

