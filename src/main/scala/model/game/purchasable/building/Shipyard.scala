package model.game.purchasable.building

import model.game.Round
import model.game.map.Coordinate
import model.game.purchasable.units.IUnit
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}

case class Shipyard(name: String = "Shipyard", 
                    roundsToComplete: Round = Round(3), 
                    cost: ResourceHolder = ResourceHolder(
                      energy = Energy(100), 
                      minerals = Minerals(100), 
                      alloys = Alloys(100)), 
                    description: String = "The Shipyard allows players to " +
                      "construct and upgrade naval units for their fleet.",
                    upkeep: ResourceHolder = ResourceHolder(energy = Energy(10), alloys = Alloys(10)),
                    output: Output = Output(ResourceHolder(minerals = Minerals(10))),
                    location: Coordinate = Coordinate()
                   ) extends IShipyard:
  override def toString: String = "Shipyard"

  def createUnit(): IUnit = ???

  override def decreaseRoundsToComplete: Shipyard =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)