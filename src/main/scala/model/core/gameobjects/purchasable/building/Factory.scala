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
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.utilities.{ResourceHolder, Round}
import utils.CirceImplicits.*

case class Factory(name: String = "Factory",
                   roundsToComplete: Round = Round(6),
                   cost: ResourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(75)),
                   description: String = "The Factory processes minerals into alloys " +
                     "which are needed for construction of buildings and ships.",
                   upkeep: ResourceHolder = ResourceHolder(energy = Energy(5), minerals = Minerals(10)),
                   output: Output = Output(rHolder = ResourceHolder(alloys = Alloys(5))),
                   location: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
                  ) extends IBuilding:

  override def toString: String = "Factory"

  override def decreaseRoundsToComplete: Factory = this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object Factory:
  implicit val encoder: Encoder[Factory] = deriveEncoder[Factory]
  implicit val decoder: Decoder[Factory] = deriveDecoder[Factory]