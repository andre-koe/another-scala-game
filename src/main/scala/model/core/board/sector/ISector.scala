package model.core.board.sector

import sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.ICoordinate
import model.core.mechanics.fleets.IFleet
import model.core.utilities.IBuildSlots
import utils.IXMLSerializable

import scala.xml.Elem

/** Represents an interface for a game sector. */
trait ISector extends IXMLSerializable:

  /** Returns the location of the sector.
   *
   *  @return An `ICoordinate` representing the sector's location.
   */
  def location: ICoordinate

  /** Returns the affiliation of the sector.
   *
   *  @return An `Affiliation` object representing the sector's affiliation.
   */
  def affiliation: Affiliation

  /** Returns the type of the sector.
   *
   *  @return A `SectorType` representing the type of the sector.
   */
  def sectorType: SectorType

  /** Returns the units currently in the sector.
   *
   * @return A Vector of `IFleet` representing the units in the sector.
   */
  def unitsInSector: Vector[IFleet]

  /** Returns the build slots of the sector.
   *
   *  @return An `IBuildSlots` representing the sector's build slots.
   */
  def buildSlots: IBuildSlots

  /** Switches the affiliation of the sector.
   *
   *  @param affiliation The new affiliation.
   *  @return A new `ISector` with the updated affiliation.
   */
  def switchAffiliation(affiliation: Affiliation): ISector

  /** Clones the sector with the specified parameters.
   *
   *  @param location The new location. Defaults to the current location if not specified.
   *  @param affiliation The new affiliation. Defaults to the current affiliation if not specified.
   *  @param sectorType The new sector type. Defaults to the current sector type if not specified.
   *  @param unitsInSector The new units in sector. Defaults to the current units if not specified.
   *  @param buildSlots The new build slots. Defaults to the current build slots if not specified.
   *  @return A new `ISector` with the updated parameters.
   */
  def cloneWith(location: ICoordinate = this.location,
                affiliation: Affiliation = this.affiliation,
                sectorType: SectorType = this.sectorType,
                unitsInSector: Vector[IFleet] = this.unitsInSector,
                buildSlots: IBuildSlots = this.buildSlots): ISector

  /** Serializes the sector to XML.
   *
   *  @return An `Elem` representing the XML serialization of the sector.
   */
  override def toXML: Elem

