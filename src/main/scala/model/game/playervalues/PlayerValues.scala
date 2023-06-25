package model.game.playervalues

import io.circe.*
import io.circe.generic.semiauto.*
import utils.CirceImplicits.*
import io.circe.syntax.EncoderOps
import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ICapacity, IResourceHolder, ResourceHolder}
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}


case class PlayerValues(resourceHolder: IResourceHolder = ResourceHolder(
                          descriptor = "Balance",
                          energy = Energy(1000),
                          minerals = Minerals(1000),
                          researchPoints = ResearchPoints(1000),
                          alloys = Alloys(10)),
                        listOfTechnologies: Vector[ITechnology] = Vector(),
                        affiliation: Affiliation = Affiliation.INDEPENDENT,
                        listOfTechnologiesCurrentlyResearched: Vector[ITechnology] = Vector(),
                        capacity: ICapacity = Capacity(3),
                        upkeep: IResourceHolder = ResourceHolder(descriptor = "Running Cost"),
                        income: IResourceHolder = ResourceHolder(descriptor = "Income")) extends IPlayerValues:

  override def extCopy(resourceHolder: IResourceHolder = resourceHolder, 
                       listOfTechnologies: Vector[ITechnology] = listOfTechnologies,
                       affiliation: Affiliation = affiliation,
                       listOfTechnologiesCurrentlyResearched: Vector[ITechnology] = listOfTechnologiesCurrentlyResearched,
                       capacity: ICapacity = capacity,
                       upkeep: IResourceHolder = upkeep,
                       income: IResourceHolder = income): IPlayerValues =
    this.copy(resourceHolder, listOfTechnologies, affiliation, listOfTechnologiesCurrentlyResearched, capacity, upkeep, income)


object PlayerValues:
  implicit val encoder: Encoder[PlayerValues] = deriveEncoder[PlayerValues]
  implicit val decoder: Decoder[PlayerValues] = deriveDecoder[PlayerValues]