package model.purchasable.units

import model.game.{Capacity, Round}
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals}

case class Cruiser( name: String = "Cruiser",
                    roundsToComplete: Round = Round(4),
                    cost: ResourceHolder = ResourceHolder(
                      energy = Energy(150), 
                      minerals = Minerals(100), 
                      alloys = Alloys(75)),
                    description: String = "Some description for Unit Cruiser",
                    upkeep: ResourceHolder = ResourceHolder(energy = Energy(12)),
                    capacity: Capacity = Capacity(4),
                    attack: Int = 70,
                    defense: Int = 140) extends IUnit:
  override def toString: String = "Cruiser"

  override def decreaseRoundsToComplete: Cruiser =
    this.copy(roundsToComplete = roundsToComplete.decrease.get)

