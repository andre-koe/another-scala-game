package model.core.board

import io.circe.*
import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.sectorutils.Affiliation
import model.core.mechanics.fleets.IFleet
import utils.IXMLSerializable

/** Represents an interface for a game board.
 * It extends the `IXMLSerializable` trait to enable XML serialization.
 */
trait IGameBoard extends IXMLSerializable:

  /** Returns the size of the game board in the X dimension.
   * 
   * @return The size of the game board in the X dimension.
   */
  def sizeX: Int

  /** Returns the size of the game board in the Y dimension.
   * 
   * @return The size of the game board in the Y dimension.
   */
  def sizeY: Int

  /** Returns the 2D vector representing the data of the game board.
   * 
   * @return The 2D vector of `ISector` that represents the game board.
   */
  def data: Vector[Vector[ISector]]

  /** Converts the sector at the given coordinate to a `PlayerSector` with the specified fleet.
   * 
   * @param coordinate The coordinate of the sector to be converted.
   * @param fleet The fleet to be placed in the `PlayerSector`.
   * @return A new `IGameBoard` with the updated sector.
   */
  def toPlayerSector(coordinate: ICoordinate, fleet: IFleet): IGameBoard

  /** Updates the game board with the provided sector.
   * 
   * @param sector The new sector to be placed on the game board.
   * @return A new `IGameBoard` with the updated sector.
   */
  def updateSector(sector: ISector): IGameBoard

  /** Performs an update on the game board.
   * 
   * @return A new `IGameBoard` reflecting the updated state.
   */
  def update: IGameBoard

  /** Gets the sector at the given coordinate.
   * 
   * @param coordinate The coordinate of the sector to be retrieved.
   * @return An `Option` containing the `ISector` at the given coordinate if it exists, `None` otherwise.
   */
  def getSectorAtCoordinate(coordinate: ICoordinate): Option[ISector]

  /** Gets all the `PlayerSector`s with the given affiliation.
   * 
   * @param affiliation The affiliation of the `PlayerSector`s to be retrieved.
   * @return A vector of `IPlayerSector`s with the given affiliation.
   */
  def getPlayerSectors(affiliation: Affiliation): Vector[IPlayerSector]

  /** Returns a vector of all sectors on the game board.
   * @return A vector of all `ISector`s on the game board.
   */
  def getSectors: Vector[ISector]

  /** Checks if a sector exists at the given coordinate.
   * 
   * @param coordinate The coordinate to check.
   * @return `true` if a sector exists at the given coordinate, `false` otherwise.
   */
  def sectorExists(coordinate: ICoordinate): Boolean

  /** Serializes the `IGameBoard` to an XML format.
   * 
   * @return An `Elem` representing the serialized XML data of the `IGameBoard`.
   */
  override def toXML: scala.xml.Elem =
    val xmlData = data.flatMap(_.map(_.toXML))
    <GameBoard>
      <SizeX>{sizeX}</SizeX>
      <SizeY>{sizeY}</SizeY>
      <Data>{xmlData}</Data>
    </GameBoard>

