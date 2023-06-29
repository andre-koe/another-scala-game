package model.core.board.sector.impl

import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.ICoordinate
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{BuildSlots, IBuildSlots, SeqOperations}

import scala.xml.Elem

/** Represents an interface for a player's game sector. */
trait IPlayerSector extends ISector:

  /** Returns the sector object.
   *
   *  @return An `ISector` object representing the sector.
   */
  def sector: ISector

  /** Returns a Vector of buildings in the construction queue.
   *
   *  @return A Vector of `IBuilding` objects in the construction queue.
   */
  def constQuBuilding: Vector[IBuilding]

  /** Returns a Vector of units in the construction queue.
   *
   *  @return A Vector of `IUnit` objects in the construction queue.
   */
  def constQuUnits: Vector[IUnit]

  /** Returns a Vector of buildings in the sector.
   *
   *  @return A Vector of `IBuilding` objects in the sector.
   */
  def buildingsInSector: Vector[IBuilding]

  /** Constructs a building in the sector.
   *
   *  @param building The building to be constructed.
   *  @return An `IPlayerSector` object representing the sector after the building construction.
   */
  def constructBuilding(building: IBuilding): IPlayerSector

  /** Constructs a unit in the sector.
   *
   *  @param rec The unit to be constructed.
   *  @param qty The quantity of units to be constructed.
   *  @return An `IPlayerSector` object representing the sector after the unit construction.
   */
  def constructUnit(rec: IUnit, qty: Int): IPlayerSector

  /** Removes units from the sector.
   *
   *  @param rec A Vector of `IUnit` objects to be removed.
   *  @return An Option of `IPlayerSector` object representing the sector after removing the units. Returns None if the operation fails.
   */
  def removeUnits(rec: Vector[IUnit]): Option[IPlayerSector]

  /** Returns the build slots of the sector.
   *
   *  @return An `IBuildSlots` representing the sector's build slots.
   */
  override def buildSlots: IBuildSlots

  /** Updates the sector.
   *
   *  @return An `IPlayerSector` object representing the updated sector.
   */
  def updateSector(): IPlayerSector

  /** Extends a copy of the sector with the specified parameters.
   *
   *  @param sector The new sector. Defaults to the current sector if not specified.
   *  @param constQuBuilding The new construction queue for buildings. Defaults to the current queue if not specified.
   *  @param constQuUnits The new construction queue for units. Defaults to the current queue if not specified.
   *  @param buildingsInSector The new buildings in sector. Defaults to the current buildings if not specified.
   *  @return A new `IPlayerSector` with the updated parameters.
   */
  def extCopy(sector: ISector = sector,
              constQuBuilding: Vector[IBuilding] = constQuBuilding,
              constQuUnits: Vector[IUnit] = constQuUnits,
              buildingsInSector: Vector[IBuilding] = buildingsInSector): IPlayerSector

  /** Serializes the player sector to XML.
   *
   *  @return An `Elem` representing the XML serialization of the player sector.
   */
  override def toXML: Elem =
    <Sector>
      <Type>
        {"PlayerSector"}
      </Type>{sector.toXML}<ConstQuBuilding>
      {constQuBuilding.map(building => {
        building.toXML
      })}
    </ConstQuBuilding>
      <ConstQuUnits>
        {constQuUnits.map(unit => {
        unit.toXML
      })}
      </ConstQuUnits>
      <Buildings>
        {buildingsInSector.map(x => {
        x.toXML
      })}
      </Buildings>
    </Sector>
