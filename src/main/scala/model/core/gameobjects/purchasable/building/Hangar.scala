package model.core.gameobjects.purchasable.building

import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import io.circe.{Decoder, Encoder}
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.utils.Output
import model.core.gameobjects.resources.resourcetypes.{Energy, Minerals}
import model.core.utilities.{Capacity, ResourceHolder, Round}
import utils.CirceImplicits.*

case class Hangar(name: String = "Hangar",
                  roundsToComplete: Round = Round(3),
                  cost: ResourceHolder = ResourceHolder(energy = Energy(50), minerals = Minerals(75)),
                  description: String = "The Hangar provides additional unit capacity.",
                  upkeep: ResourceHolder = ResourceHolder(energy = Energy(3), minerals = Minerals(5)),
                  output: Output = Output(cap = Capacity(10)),
                  location: ICoordinate = Coordinate(-1,-1)
                 ) extends IBuilding:
  
  override def toString: String = "Hangar"
  
  override def decreaseRoundsToComplete: Hangar =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object Hangar:
  implicit val encoder: Encoder[Hangar] = deriveEncoder[Hangar]
  implicit val decoder: Decoder[Hangar] = deriveDecoder[Hangar]