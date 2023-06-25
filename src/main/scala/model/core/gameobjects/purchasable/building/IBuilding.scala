package model.core.gameobjects.purchasable.building

import model.core.board.sector.ISector
import utils.CirceImplicits.*
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.utils.IOutput
import model.core.utilities.IResourceHolder
import model.core.utilities.interfaces.{ILocatable, IPurchasable, IRoundBasedConstructable, IUpkeep}
import utils.IXMLSerializable

import scala.xml.Elem

trait IBuilding extends IUpkeep,
  ILocatable, IGameObject, IRoundBasedConstructable, IPurchasable, IXMLSerializable:

  def output: IOutput

  override def toXML: scala.xml.Elem =
    <Building>
      <Name>{name}</Name>
      <Round>{roundsToComplete.value}</Round>
      {location.toXML}
    </Building>