package model.core.gameobjects.purchasable.building

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import utils.CirceImplicits._
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.utils.Output
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ResourceHolder, Round}

case class ResearchLab(name: String = "Research Lab",
                       roundsToComplete: Round = Round(3),
                       cost: ResourceHolder = ResourceHolder(
                         energy = Energy(100),
                         minerals = Minerals(100),
                         alloys = Alloys(100)),
                       description: String = "The Research Lab increases research output.",
                       upkeep: ResourceHolder = ResourceHolder(energy = Energy(10), alloys = Alloys(10)),
                       output: Output = Output(ResourceHolder(researchPoints = ResearchPoints(20))),
                       location: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
                      ) extends IBuilding:
  
  override def toString: String = "Research Lab"

  override def decreaseRoundsToComplete: ResearchLab = this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object ResearchLab:
  implicit val encoder: Encoder[ResearchLab] = deriveEncoder[ResearchLab]
  implicit val decoder: Decoder[ResearchLab] = deriveDecoder[ResearchLab]