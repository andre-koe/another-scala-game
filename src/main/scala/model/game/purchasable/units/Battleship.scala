package model.game.purchasable.units

import model.game.map.Coordinate
import model.game.purchasable.IGameObject
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.game.{Capacity, Round}

case class Battleship(name: String = "Battleship",
                      roundsToComplete: Round = Round(6),
                      cost: ResourceHolder = ResourceHolder(
                        energy = Energy(200), 
                        minerals = Minerals(50), 
                        alloys = Alloys(200)),
                      description: String = "Some description for Unit Battleship",
                      upkeep: ResourceHolder = ResourceHolder(energy = Energy(20)),
                      capacity: Capacity = Capacity(10),
                      firepower: Int = 500,
                      speed: Int = 1,
                      location: Coordinate
                     ) extends IUnit:
  override def toString: String =
    "Battleship"

  override def move(target: Coordinate): IUnit = ???


  override def decreaseRoundsToComplete: Battleship = 
    this.copy(roundsToComplete = roundsToComplete.decrease.get)
  
  
