package model.core.gameobjects.purchasable.units

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, IRound, ResourceHolder, Round}
import model.core.gameobjects.resources.resourcetypes.{Energy, Minerals}


case class Destroyer(name: String = "Destroyer",
                     roundsToComplete: IRound = Round(3),
                     cost: IResourceHolder = ResourceHolder(energy = Energy(130), minerals = Minerals(90)),
                     description: String = "Some description for Unit Destroyer",
                     upkeep: IResourceHolder = ResourceHolder(energy = Energy(8)),
                     capacity: ICapacity = Capacity(2),
                     firepower: Int = 140,
                     speed: Int = 3,
                     location: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
                    ) extends IUnit:

  override def toString: String = "Destroyer"

  override def decreaseRoundsToComplete: Destroyer = this.copy(roundsToComplete = roundsToComplete.decrease.get)

