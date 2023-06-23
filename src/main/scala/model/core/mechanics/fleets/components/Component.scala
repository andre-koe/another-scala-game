package model.core.mechanics.fleets.components

import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.gameobjects.purchasable.IGameObject
import model.core.mechanics.MoveVector
import model.core.utilities.ICapacity
import model.core.utilities.interfaces.{ILocatable, IPurchasable, IUpkeep}
import utils.IXMLSerializable

trait Component extends IUpkeep, IGameObject, IPurchasable, ILocatable, IXMLSerializable:

  def firepower: Int

  def speed: Int

  def location: ISector
  
  def capacity: ICapacity