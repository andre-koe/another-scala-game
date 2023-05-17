package model.core.sector

import model.core.sector.decorator.PlayerSector
import model.game.BuildSlots
import model.game.map.system.Affiliation
import model.game.map.system.SectorType
import model.game.map.Coordinate

import scala.io.AnsiColor

case class Sector(location: Coordinate, affiliation: Affiliation, sectorType: SectorType) extends ISector:

  override def switchAffiliation(affiliation: Affiliation): ISector =
    affiliation match
      case Affiliation.PLAYER => 
        PlayerSector(
          location = location, 
          affiliation = affiliation, 
          sectorType = sectorType, 
          buildingsInProduction = List(), 
          buildingsInSector = List(), 
          unitsInSector = List(), 
          buildingSlots = mapBuildSlots)
      case _ => this.copy(affiliation = affiliation)

  override def toString: String =
    val color = affiliation match
      case Affiliation.PLAYER => AnsiColor.BLUE
      case Affiliation.ENEMY => AnsiColor.RED
      case Affiliation.INDEPENDENT => AnsiColor.WHITE

    val style = sectorType match
      case SectorType.REGULAR => s"($location)"
      case SectorType.BASE => s"[$location]"

    color + style + AnsiColor.RESET
    
  override def mapBuildSlots: BuildSlots = sectorType match
    case SectorType.BASE => BuildSlots(10)
    case _ => BuildSlots(5)

