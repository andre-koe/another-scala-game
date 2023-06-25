package model.core.board.sector

import sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.ICoordinate
import model.core.mechanics.fleets.IFleet
import model.core.utilities.IBuildSlots
import utils.IXMLSerializable

import scala.xml.Elem

trait ISector extends IXMLSerializable:

  def location: ICoordinate
  
  def affiliation: Affiliation
  
  def sectorType: SectorType

  def unitsInSector: Vector[IFleet]

  def buildSlots: IBuildSlots
  
  def switchAffiliation(affiliation: Affiliation): ISector
  
  def cloneWith(location: ICoordinate = this.location,
                affiliation: Affiliation = this.affiliation,
                sectorType: SectorType = this.sectorType,
                unitsInSector: Vector[IFleet] = this.unitsInSector,
                buildSlots: IBuildSlots = this.buildSlots): ISector

  override def toXML: Elem
