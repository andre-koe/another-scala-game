package model.game.purchasable.units

import model.game.map.Coordinate
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, Minerals}
import model.game.{Capacity, Round}

case class Destroyer(name: String = "Destroyer",
                     roundsToComplete: Round = Round(3),
                     cost: ResourceHolder = ResourceHolder(energy = Energy(130), minerals = Minerals(90)),
                     description: String = "Some description for Unit Destroyer",
                     upkeep: ResourceHolder = ResourceHolder(energy = Energy(8)),
                     capacity: Capacity = Capacity(2),
                     firepower: Int = 140,
                     speed: Int = 3,
                     location: Coordinate) extends IUnit:

  override def toString: String = "Destroyer"

  override def decreaseRoundsToComplete: Destroyer =
    this.copy(roundsToComplete = roundsToComplete.decrease.get)

  override def move(target: Coordinate): IUnit = ???

