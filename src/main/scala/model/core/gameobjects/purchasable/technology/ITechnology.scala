package model.core.gameobjects.purchasable.technology

import io.circe.generic.auto.*
import model.core.gameobjects.purchasable.IGameObject
import model.core.utilities.{IResourceHolder, IRound, Round}
import model.core.utilities.interfaces.{IPurchasable, IRoundBasedConstructable}
import utils.IXMLSerializable

import scala.xml.Elem


trait ITechnology extends IGameObject, IPurchasable, IRoundBasedConstructable, IXMLSerializable:

  override def toXML: scala.xml.Elem =
    <Technology>
      <Name>{name}</Name>
      <Round>{roundsToComplete.value}</Round>
    </Technology>

