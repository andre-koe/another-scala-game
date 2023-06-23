package model.core.gameobjects.purchasable.building

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import utils.CirceImplicits._
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.utils.Output
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ResourceHolder, Round}

case class Shipyard(name: String = "Shipyard", 
                    roundsToComplete: Round = Round(3), 
                    cost: ResourceHolder = ResourceHolder(
                      energy = Energy(100), 
                      minerals = Minerals(100), 
                      alloys = Alloys(100)), 
                    description: String = "The Shipyard allows players to " +
                      "construct and upgrade naval units for their fleet.",
                    upkeep: ResourceHolder = ResourceHolder(energy = Energy(10), alloys = Alloys(10)),
                    output: Output = Output(ResourceHolder(minerals = Minerals(10))),
                    location: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
                   ) extends IShipyard:
  
  override def toString: String = "Shipyard"

  def createUnit(): IUnit = ???

  override def decreaseRoundsToComplete: Shipyard =
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object Shipyard:
  implicit val encoder: Encoder[Shipyard] = deriveEncoder[Shipyard]
  implicit val decoder: Decoder[Shipyard] = deriveDecoder[Shipyard]