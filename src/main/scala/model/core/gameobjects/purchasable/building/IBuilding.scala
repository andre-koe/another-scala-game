package model.core.gameobjects.purchasable.building

import model.core.board.sector.ISector
import utils.CirceImplicits.*
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.utils.IOutput
import model.core.utilities.IResourceHolder
import model.core.utilities.interfaces.{ILocatable, IPurchasable, IRoundBasedConstructable, IUpkeep}
import utils.IXMLSerializable

import scala.xml.Elem

/** Represents a Building in the game. */
trait IBuilding extends IUpkeep, ILocatable, IGameObject, IRoundBasedConstructable, IPurchasable, IXMLSerializable:

  /** Retrieves the output of the building.
   *
   *  @return the output of the building.
   */
  def output: IOutput

  /** Serializes the current state of the Building to XML format.
   *
   *  @return an XML representation of the Building's current state.
   */
  override def toXML: scala.xml.Elem =
    <Building>
      <Name>{name}</Name>
      <Round>{roundsToComplete.value}</Round>
      {location.toXML}
    </Building>