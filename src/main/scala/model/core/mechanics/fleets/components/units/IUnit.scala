package model.core.mechanics.fleets.components.units

import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.units.*
import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.interfaces.{IPurchasable, IRoundBasedConstructable, IUpkeep}
import model.core.utilities.{Capacity, ICapacity, Round}
import utils.IXMLSerializable

import scala.xml.Elem

/** Interface for all game units.
 *
 *  All units must implement IUpkeep, IGameObject, IPurchasable, IXMLSerializable, and IRoundBasedConstructable interfaces.
 */
trait IUnit extends IUpkeep, IGameObject, IPurchasable, IXMLSerializable, IRoundBasedConstructable:

  /** Retrieves the firepower of the unit.
   *  @return Int: The firepower of the unit.
   */
  def firepower: Int

  /** Retrieves the speed of the unit.
   *  @return Int: The speed of the unit.
   */
  def speed: Int

  /** Retrieves the capacity of the unit.
   *  @return ICapacity: The capacity of the unit.
   */
  def capacity: ICapacity

  /** Converts the unit to XML format.
   *  @return Elem: The XML representation of the unit.
   */
  override def toXML: Elem =
    <Unit>
      <Type>{"Unit"}</Type>
      <Name>{name}</Name>
      <Rounds>{roundsToComplete.value}</Rounds>
    </Unit>
