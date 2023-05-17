package model.game.purchasable.units

import model.game.map.Coordinate
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.game.{Capacity, Round}

case class Cruiser( name: String = "Cruiser",
                    roundsToComplete: Round = Round(4),
                    cost: ResourceHolder = ResourceHolder(
                      energy = Energy(150), 
                      minerals = Minerals(100), 
                      alloys = Alloys(75)),
                    description: String = "Some description for Unit Cruiser",
                    upkeep: ResourceHolder = ResourceHolder(energy = Energy(12)),
                    capacity: Capacity = Capacity(4),
                    firepower: Int = 210,
                    speed: Int = 2,
                    location: Coordinate
                  ) extends IUnit:
  override def toString: String = "Cruiser"

  override def move(target: Coordinate): IUnit = ???

  override def decreaseRoundsToComplete: Cruiser =
    this.copy(roundsToComplete = roundsToComplete.decrease.get)

