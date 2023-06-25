package model.core.gameobjects.purchasable.units

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, IRound, ResourceHolder, Round}


case class Battleship(name: String = "Battleship",
                      roundsToComplete: IRound = Round(6),
                      cost: IResourceHolder = ResourceHolder(
                        energy = Energy(200), 
                        minerals = Minerals(50), 
                        alloys = Alloys(200)),
                      description: String = "Some description for Unit Battleship",
                      upkeep: IResourceHolder = ResourceHolder(energy = Energy(20)),
                      capacity: ICapacity = Capacity(10),
                      firepower: Int = 500,
                      speed: Int = 1) extends IUnit:
  override def toString: String = "Battleship"

  override def decreaseRoundsToComplete: Battleship = this.copy(roundsToComplete = roundsToComplete.decrease.get)
  
