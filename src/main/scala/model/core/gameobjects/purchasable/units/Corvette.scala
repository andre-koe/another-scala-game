package model.core.gameobjects.purchasable.units

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.resources.resourcetypes.{Energy, Minerals}
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, IRound, ResourceHolder, Round}


case class Corvette(name: String = "Corvette",
                    roundsToComplete: IRound = Round(2),
                    cost: IResourceHolder = ResourceHolder(energy = Energy(70), minerals = Minerals(30)),
                    description: String = "Some description for Unit Corvette",
                    upkeep: IResourceHolder = ResourceHolder(energy = Energy(1)),
                    capacity: ICapacity = Capacity(1),
                    firepower: Int = 14,
                    speed: Int = 4,
                    location: ISector = Sector(Coordinate(-1,-1),Affiliation.INDEPENDENT,SectorType.REGULAR)
                   ) extends IUnit:
  
  override def toString: String = "Corvette"
  
  override def decreaseRoundsToComplete: Corvette = this.copy(roundsToComplete = roundsToComplete.decrease.get)