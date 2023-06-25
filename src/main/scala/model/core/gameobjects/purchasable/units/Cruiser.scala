package model.core.gameobjects.purchasable.units

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, IRound, ResourceHolder, Round}
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}


case class Cruiser(name: String = "Cruiser",
                   roundsToComplete: IRound = Round(4),
                   cost: IResourceHolder = ResourceHolder(
                      energy = Energy(150), 
                      minerals = Minerals(100), 
                      alloys = Alloys(75)),
                   description: String = "Some description for Unit Cruiser",
                   upkeep: IResourceHolder = ResourceHolder(energy = Energy(12)),
                   capacity: ICapacity = Capacity(4),
                   firepower: Int = 210,
                   speed: Int = 2) extends IUnit:
  
  override def toString: String = "Cruiser"
  
  override def decreaseRoundsToComplete: Cruiser = this.copy(roundsToComplete = roundsToComplete.decrease.get)

