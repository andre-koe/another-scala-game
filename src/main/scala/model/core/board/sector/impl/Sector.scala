package model.core.board.sector.impl

import io.circe.{Decoder, Encoder}
import io.circe.generic.auto.*
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}
import utils.CirceImplicits.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.mechanics.fleets.{Fleet, IFleet}
import model.core.utilities.{BuildSlots, IBuildSlots}

import scala.io.AnsiColor
import scala.xml.Elem

case class Sector(location: ICoordinate, affiliation: Affiliation = Affiliation.PLAYER,
                  sectorType: SectorType = SectorType.REGULAR, unitsInSector: Vector[IFleet] = Vector()) extends ISector:

  override def switchAffiliation(affiliation: Affiliation): ISector =
    affiliation match
      case Affiliation.PLAYER => 
        PlayerSector(this, constQuBuilding = Vector(), buildingsInSector = Vector())
      case _ => this.copy(affiliation = affiliation)

  override def buildSlots: IBuildSlots = sectorType match
    case SectorType.BASE => BuildSlots(10)
    case _ => BuildSlots(5)

  override def cloneWith(location: ICoordinate = this.location,
                    affiliation: Affiliation = this.affiliation,
                    sectorType: SectorType = this.sectorType,
                    unitsInSector: Vector[IFleet] = this.unitsInSector,
                    buildSlots: IBuildSlots = this.buildSlots): ISector =
    Sector(location, affiliation, sectorType, unitsInSector)


  override def toString: String =
    val color = affiliation match
      case Affiliation.PLAYER => AnsiColor.BLUE
      case Affiliation.ENEMY => AnsiColor.RED
      case Affiliation.INDEPENDENT => AnsiColor.WHITE

    val style = sectorType match
      case SectorType.REGULAR => s"($location)"
      case SectorType.BASE => s"[$location]"

    color + style + AnsiColor.RESET


  override def toXML: Elem =
    <Sector>
      <Type>
        {"Sector"}
      </Type>
      <Location>
        {location.toXML}
      </Location>
      <Affiliation>
        {affiliation.toString}
      </Affiliation>
      <SectorType>
        {sectorType.toString}
      </SectorType>
      <UnitsInSector>
        {unitsInSector.map(_.toXML)}
      </UnitsInSector>
    </Sector>


object Sector:
  implicit val encoder: Encoder[Sector] = deriveEncoder[Sector]
  implicit val decoder: Decoder[Sector] = deriveDecoder[Sector]