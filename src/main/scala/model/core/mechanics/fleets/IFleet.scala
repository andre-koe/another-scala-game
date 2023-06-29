package model.core.mechanics.fleets

import model.core.board.boardutils.ICoordinate
import model.core.board.sector.ISector
import model.core.gameobjects.purchasable.IGameObject
import model.core.mechanics.IMoveVector
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.{ILocatable, IMovable, IPurchasable, IUpkeep}
import model.core.utilities.{IAffiliated, ICapacity, IResourceHolder}
import utils.IXMLSerializable

import scala.xml.Elem

/** Interface for Fleet functionality.
 *
 * This trait represents a fleet with various properties and capabilities.
 */
trait IFleet extends IUpkeep, IGameObject, IXMLSerializable, IMovable, ILocatable, IAffiliated:

  /** Gets the name of the fleet. 
   *
   * @return A String representing the name of the fleet.
   */
  def name: String

  /** Gets the units comprising the fleet. 
   *
   * @return A Vector of IUnit that represents the components of the fleet.
   */
  def fleetComponents: Vector[IUnit]

  /** Gets the current location of the fleet. 
   *
   * @return An ICoordinate representing the location of the fleet.
   */
  def location: ICoordinate

  /** Gets the current movement vector of the fleet. 
   *
   * @return An IMoveVector representing the movement vector of the fleet.
   */
  def moveVector: IMoveVector

  /** Gets the firepower of the fleet. 
   *
   * @return An Int representing the firepower of the fleet.
   */
  def firepower: Int

  /** Gets the speed of the fleet. 
   *
   * @return An Int representing the speed of the fleet.
   */
  def speed: Int

  /** Gets the upkeep cost of the fleet.
   *
   * @return An IResourceHolder representing the upkeep cost of the fleet.
   */
  def upkeep: IResourceHolder

  /** Removes specific units from the fleet. 
   *
   * @param tbr A Vector of IUnit that should be removed from the fleet.
   * @return An Option that contains the updated IFleet if units were successfully removed, None otherwise.
   */
  def remove(tbr: Vector[IUnit]): Option[IFleet]

  /** Gets the units in the fleet.
   *
   * @return A Vector of IUnit that represents the units in the fleet.
   */
  def units: Vector[IUnit]

  /** Merges another fleet with this fleet.
   *
   * @param f The other IFleet to be merged with this fleet.
   * @return A new IFleet that results from the merge.
   */
  def merge(f: IFleet): IFleet

  /** Gets the carrying capacity of the fleet.
   *
   * @return An ICapacity representing the carrying capacity of the fleet.
   */
  def capacity: ICapacity

  /** Splits the fleet into two fleets.
   *
   * @return A tuple containing two IFleet resulting from the split.
   */
  def split(): (IFleet, IFleet)

  /** Creates a copy of the fleet with specified parameters.
   *
   * @param name The name of the fleet.
   * @param fleetComponents The components of the fleet.
   * @param location The location of the fleet.
   * @param moveVector The move vector of the fleet.
   * @return A new IFleet with the specified parameters.
   */
  def extCopy(name: String = name,
              fleetComponents: Vector[IUnit] = fleetComponents,
              location: ICoordinate = location,
              moveVector: IMoveVector = moveVector): IFleet

  /** Method to convert the fleet to XML format.
   *
   * @return Elem: The XML representation of the fleet.
   */
  override def toXML: Elem =
    <Fleet>
      <Type>{"Fleet"}</Type>
      <Name>{name}</Name>
      <Location>{location.toXML}</Location>
      <MoveVector>{moveVector.toXML}</MoveVector>
      <Affiliation>{affiliation.toString}</Affiliation>
      <FleetComponents>
        {fleetComponents.map(_.toXML)}
      </FleetComponents>
    </Fleet>