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

trait IUnit extends IUpkeep, IGameObject, IPurchasable, IXMLSerializable, IRoundBasedConstructable:

  def firepower: Int

  def speed: Int

  def capacity: ICapacity

  override def toXML: Elem =
    <Unit>
      <Type>{"Unit"}</Type>
      <Name>{name}</Name>
      <Rounds>{roundsToComplete.value}</Rounds>
    </Unit>

