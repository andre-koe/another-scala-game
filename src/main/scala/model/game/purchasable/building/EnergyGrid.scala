package model.game.purchasable.building

import model.game.Round
import model.game.purchasable.IGameObject
import model.game.purchasable.utils.Output
import model.game.map.Coordinate
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, Minerals}

case class EnergyGrid(name: String = "Energy Grid",
                      roundsToComplete: Round = Round(6),
                      cost: ResourceHolder = ResourceHolder(minerals = Minerals(75)),
                      description: String = "The Energy Grid " +
                        "provides a steady stream of energy to power buildings and units.",
                      upkeep: ResourceHolder = ResourceHolder(energy = Energy(3)),
                      output: Output = Output(rHolder = ResourceHolder(energy = Energy(10))),
                      location: Coordinate = Coordinate(0,0),
                     ) extends IBuilding:
  
  override def toString: String = "Energy Grid"

  override def decreaseRoundsToComplete: EnergyGrid = 
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)

