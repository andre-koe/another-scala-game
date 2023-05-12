package model.game.map.system

import SectorType.*
import Affiliation.*
import model.game.BuildSlots
import model.game.map.Coordinate
import model.game.purchasable.units.IUnit

import scala.io
import scala.io.AnsiColor

case class Sector(coordinate: Coordinate,
                  affiliation: Affiliation = Affiliation.INDEPENDENT,
                  sectorType: SectorType = REGULAR,
                  units: Option[Vector[IUnit]] = None):

  def buildSlots: BuildSlots =
    sectorType match
      case REGULAR => BuildSlots(3)
      case BASE => BuildSlots(7)
      
  def description: String = sectorType match
    case BASE => descriptionBuilder("Home System")
    case REGULAR => descriptionBuilder("Regular System")
    
  def changeAffiliation(newAffiliation: Affiliation): Sector =
    this.copy(affiliation = newAffiliation)
    
  override def toString: String =
    affiliation match
      case INDEPENDENT => AnsiColor.WHITE + systemTypeToString + AnsiColor.RESET
      case PLAYER => AnsiColor.BLUE + systemTypeToString + AnsiColor.RESET
      case ENEMY => AnsiColor.RED + systemTypeToString + AnsiColor.RESET
      
  private def descriptionBuilder(str:  String): String =
    affiliation match
      case INDEPENDENT => s"$str, provides up to $buildSlots | Independent"
      case PLAYER => s"$str, provides up to $buildSlots | Belongs to the Player"
      case ENEMY => s"$str, provides up to $buildSlots | Belongs to the Enemy"
      
  private def systemTypeToString: String =
    this.sectorType match
      case BASE => s"[$coordinate]"
      case REGULAR => s"($coordinate)"