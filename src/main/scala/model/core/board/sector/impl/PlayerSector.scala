package model.core.board.sector.impl

import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import io.circe.syntax.*
import io.circe.{Decoder, Encoder}
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.mechanics.fleets.components.Component
import model.core.mechanics.fleets.components.units.IUnit
import model.core.mechanics.fleets.{Fleet, IFleet}
import model.core.utilities.{BuildSlots, IBuildSlots, SeqOperations}
import utils.CirceImplicits.*

import scala.annotation.tailrec
import scala.xml.Elem


case class PlayerSector(sector: ISector,
                        constQuBuilding: Vector[IBuilding] = Vector(),
                        constQuUnits: Vector[IUnit] = Vector(),
                        buildingsInSector: Vector[IBuilding] = Vector()) extends IPlayerSector:

  override def affiliation: Affiliation = sector.affiliation

  override def sectorType: SectorType = sector.sectorType

  override def unitsInSector: Vector[IFleet] = sector.unitsInSector

  override def location: ICoordinate = sector.location
  
  override def cloneWith(location: ICoordinate = this.location, affiliation: Affiliation = this.affiliation,
                         sectorType: SectorType = this.sectorType, unitsInSector: Vector[IFleet] = this.unitsInSector,
                         buildSlots: IBuildSlots = this.buildSlots): ISector =

    this.sector.cloneWith(location = location, affiliation = affiliation, sectorType = sectorType,
      unitsInSector = unitsInSector, buildSlots = buildSlots)

  override def extCopy(sector: ISector = sector, 
                       constQuBuilding: Vector[IBuilding] = constQuBuilding,
                       constQuUnits: Vector[IUnit] = constQuUnits,
                       buildingsInSector: Vector[IBuilding] = buildingsInSector): IPlayerSector =
    this.copy(sector, constQuBuilding, constQuUnits, buildingsInSector)

  override def switchAffiliation(affiliation: Affiliation): ISector = affiliation match
      case Affiliation.PLAYER => this
      case _ => Sector(location, affiliation, sector.sectorType)

  def constructBuilding(building: IBuilding): IPlayerSector =
    sector.buildSlots.decrement match
      case Some(x) => handleBuilding(building, x)
      case _ => this

  private def handleBuilding(building: IBuilding, newBuildSlots: IBuildSlots): IPlayerSector =
    val buildingsInConstruction = this.constQuBuilding.:+(BuildingFactory(building.name.toLowerCase, this).get)
    this.copy(sector = sector.cloneWith(buildSlots = newBuildSlots), constQuBuilding = buildingsInConstruction)

  def constructUnit(rec: IUnit, qty: Int): IPlayerSector =
    val unitsInConstruction = this.constQuUnits ++ Vector.tabulate(qty){_ => UnitFactory(rec.name.toLowerCase, this).get}
    this.copy(constQuUnits = unitsInConstruction)

  def removeUnits(rec: Vector[IUnit]): Option[IPlayerSector] =
    @tailrec
    def removeRecursively(units: Vector[IFleet], rec: Vector[IUnit]): Vector[IFleet] =
      if (rec.isEmpty || units.isEmpty) units
      else {
        val updatedFleet = units.head.remove(rec)
        val updatedFleets = updatedFleet match
          case Some(x) => units.tail.+:(x)
          case _ => units.tail
        val updatedRec = rec.diff(units.head.units)
        removeRecursively(updatedFleets, updatedRec)
      }

    val nFleets = removeRecursively(sector.unitsInSector, rec)
    if (nFleets == sector.unitsInSector) then None
    else Some(this.copy(sector = sector.cloneWith(unitsInSector = nFleets)))


  private def assignUnits(newUnits: Vector[IUnit]): IPlayerSector = {
    val updatedFleets = if (newUnits.nonEmpty) {
      if (sector.unitsInSector.isEmpty) {
        Vector(Fleet(fleetComponents = newUnits, location = this))
      } else {
        sector.unitsInSector.map(existingFleet =>
          existingFleet.extCopy(fleetComponents = existingFleet.fleetComponents ++ newUnits))
      }
    } else {
      unitsInSector
    }
    this.copy(sector = sector.cloneWith(unitsInSector = updatedFleets))
  }

  override def buildSlots: BuildSlots =
    BuildSlots(sector.buildSlots.value - buildingsInSector.length - constQuBuilding.length)

  def updateSector(): IPlayerSector =
    val (buildingsStillUnderConstruction, finishedBuilding) = SeqOperations().partitionOnRounds0(constQuBuilding)
    val (unitsStillUnderConstruction, finishedUnits) = SeqOperations().partitionOnRounds0(constQuUnits)
    this.copy(
      buildingsInSector = buildingsInSector ++ finishedBuilding,
      constQuBuilding = buildingsStillUnderConstruction.toVector,
      constQuUnits = unitsStillUnderConstruction.toVector
    ).assignUnits(finishedUnits.toVector)


  override def toString: String = sector.toString

object PlayerSector:
  implicit val encoder: Encoder[PlayerSector] = deriveEncoder[PlayerSector]
  implicit val decoder: Decoder[PlayerSector] = deriveDecoder[PlayerSector]


