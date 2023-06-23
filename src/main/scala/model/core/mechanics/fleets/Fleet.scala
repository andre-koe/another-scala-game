package model.core.mechanics.fleets

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.IGameObject
import model.core.mechanics.fleets.components.Component
import model.core.mechanics.fleets.components.units.IUnit
import model.core.mechanics.{IMoveVector, MoveVector}
import model.core.utilities.*

import scala.util.Random
case class Fleet(name: String = "Battlegroup-" + Random().between(1,1000),
                 fleetComponents: Vector[IUnit] = Vector(),
                 location: ISector = Sector(location = Coordinate(-1,-1)),
                 moveVector: IMoveVector = MoveVector()) extends IFleet:

  override def firepower: Int = fleetComponents.map(_.firepower).sum

  override def speed: Int = fleetComponents.map(_.speed).sortWith(_ < _).head

  override def upkeep: IResourceHolder =
    if fleetComponents.length > 1 then fleetComponents.map(_.upkeep).reduce((x,y) => x.increase(y))
    else if fleetComponents.length == 1 then fleetComponents.head.upkeep
    else ResourceHolder()

  def remove(tbr: Vector[IUnit]): Option[IFleet] =
    if fleetComponents.diff(tbr).nonEmpty then Some(this.copy(fleetComponents = fleetComponents.diff(tbr)))
    else None

  def units: Vector[IUnit] = fleetComponents

  def merge(f: IFleet): IFleet = this.copy(fleetComponents = fleetComponents.:++(f.fleetComponents))
  
  def capacity: ICapacity =
    if fleetComponents.length > 1 then fleetComponents.map(_.capacity).reduce((x,y) => x.increase(y))
    else if fleetComponents.length == 1 then fleetComponents.head.capacity
    else Capacity()

  override def description: String =
    s"""${name}
      |Firepower: ${firepower}
      |Size: ${units.length}
      |""".stripMargin

  override def cost: IResourceHolder =
    if fleetComponents.length > 1 then fleetComponents.map(_.cost).reduce((x, y) => x.increase(y))
    else if fleetComponents.length == 1 then fleetComponents.head.cost
    else ResourceHolder()

  override def split(): (IFleet, IFleet) =
    val (f1: Vector[IUnit], f2: Vector[IUnit]) = fleetComponents.splitAt(fleetComponents.length / 2)
    (Fleet(fleetComponents = f1), Fleet(fleetComponents = f2))

  override def getMove: IMoveVector = moveVector

  override def move: Option[IMoveVector] = Some(moveVector)

  override def extCopy(name: String, fleetComponents: Vector[IUnit], location: ISector, moveVector: IMoveVector): IFleet =
    this.copy(name, fleetComponents, location, moveVector)