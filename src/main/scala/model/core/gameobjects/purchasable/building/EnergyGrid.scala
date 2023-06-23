package model.core.gameobjects.purchasable.building

import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import io.circe.{Decoder, Encoder}
import model.core.board.boardutils.Coordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.technology.NanoRobotics
import model.core.gameobjects.purchasable.utils.Output
import model.core.gameobjects.resources.resourcetypes.{Energy, Minerals}
import model.core.utilities.{ResourceHolder, Round}
import utils.CirceImplicits.*


case class EnergyGrid(name: String = "Energy Grid",
                      roundsToComplete: Round = Round(6),
                      cost: ResourceHolder = ResourceHolder(minerals = Minerals(75)),
                      description: String = "The Energy Grid " +
                        "provides a steady stream of energy to power buildings and units.",
                      upkeep: ResourceHolder = ResourceHolder(energy = Energy(3)),
                      output: Output = Output(rHolder = ResourceHolder(energy = Energy(10))),
                      location: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
                     ) extends IBuilding:
  
  override def toString: String = s"Energy Grid"

  override def decreaseRoundsToComplete: EnergyGrid = 
    this.copy(roundsToComplete = this.roundsToComplete.decrease.get)


object EnergyGrid:
  implicit val encoder: Encoder[EnergyGrid] = deriveEncoder[EnergyGrid]
  implicit val decoder: Decoder[EnergyGrid] = deriveDecoder[EnergyGrid]

