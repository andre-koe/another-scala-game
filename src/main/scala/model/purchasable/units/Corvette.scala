package model.purchasable.units

import model.game.{Capacity, Round}
import model.purchasable.units.IUnit
import model.resources.ResourceHolder
import model.resources.resourcetypes.Minerals
import model.resources.resourcetypes.Energy

case class Corvette(name: String = "Corvette",
                    roundsToComplete: Round = Round(2),
                    cost: ResourceHolder = ResourceHolder(energy = Energy(70), minerals = Minerals(30)),
                    description: String = "Some description for Unit Corvette",
                    upkeep: ResourceHolder = ResourceHolder(energy = Energy(1)),
                    capacity: Capacity = Capacity(1),
                    attack: Int = 10,
                    defense: Int = 4
                   ) extends IUnit:
  override def toString: String = "Corvette"

  override def decreaseRoundsToComplete: Corvette =
    this.copy(roundsToComplete = roundsToComplete.decrease.get)