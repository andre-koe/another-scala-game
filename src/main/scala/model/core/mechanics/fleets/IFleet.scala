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

trait IFleet extends IUpkeep, IGameObject, IXMLSerializable, IMovable, ILocatable, IAffiliated :

  def name: String

  def fleetComponents: Vector[IUnit]

  def location: ICoordinate

  def moveVector: IMoveVector

  def firepower: Int

  def speed: Int

  def upkeep: IResourceHolder

  def remove(tbr: Vector[IUnit]): Option[IFleet]

  def units: Vector[IUnit]

  def merge(f: IFleet): IFleet

  def capacity: ICapacity

  def split(): (IFleet, IFleet)

  def extCopy(name: String = name,
              fleetComponents: Vector[IUnit] = fleetComponents,
              location: ICoordinate = location,
              moveVector: IMoveVector = moveVector): IFleet

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