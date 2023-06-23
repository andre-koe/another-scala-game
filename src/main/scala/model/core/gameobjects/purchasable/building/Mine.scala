package model.core.gameobjects.purchasable.building

import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import io.circe.{Decoder, Encoder}
import model.core.board.boardutils.Coordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.utils.Output
import model.core.gameobjects.resources.resourcetypes.{Energy, Minerals}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{IResourceHolder, IRound, ResourceHolder, Round}
import utils.CirceImplicits.*

case class Mine(name: String = "Mine",
                roundsToComplete: IRound = Round(3),
                cost: IResourceHolder = ResourceHolder(energy = Energy(80)),
                description: String = "The Mine extracts minerals which are used for " +
                  "the construction of units and buildings. " +
                  "Minerals are the base resource for the production of alloys.",
                upkeep: IResourceHolder = ResourceHolder(energy = Energy(10)),
                output: Output = Output(rHolder = ResourceHolder(minerals = Minerals(10))),
                location: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
               ) extends IBuilding:
  
  override def toString: String = "Mine"

  override def decreaseRoundsToComplete: Mine =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object Mine:
  implicit val encoder: Encoder[Mine] = deriveEncoder[Mine]
  implicit val decoder: Decoder[Mine] = deriveDecoder[Mine]