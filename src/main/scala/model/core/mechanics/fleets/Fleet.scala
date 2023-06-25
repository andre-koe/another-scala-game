package model.core.mechanics.fleets

import io.circe.generic.auto.*
import io.circe.syntax.*
import model.core.board.sector.impl.Sector
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.board.sector.sectorutils.Affiliation
import model.core.gameobjects.purchasable.IGameObject
import model.core.mechanics.fleets.components.units.IUnit
import model.core.mechanics.{IMoveVector, MoveVector}
import model.core.utilities.*

import scala.util.Random
case class Fleet(name: String = "Battlegroup-" + Random().between(1,1000),
                 fleetComponents: Vector[IUnit] = Vector(),
                 location: ICoordinate = Coordinate(-1,-1),
                 moveVector: IMoveVector = MoveVector(),
                 affiliation: Affiliation = Affiliation.PLAYER) extends IFleet:

  override def firepower: Int = fleetComponents.map(_.firepower).sum

  override def speed: Int = fleetComponents.map(_.speed).sortWith(_ < _).head

  override def upkeep: IResourceHolder =
    fleetComponents.map(_.upkeep).foldLeft(ResourceHolder())((x: IResourceHolder, y: IResourceHolder) => x.increase(y))

  def remove(tbr: Vector[IUnit]): Option[IFleet] =
    if fleetComponents.diff(tbr).nonEmpty then Some(this.copy(fleetComponents = fleetComponents.diff(tbr)))
    else None

  def units: Vector[IUnit] = fleetComponents

  def merge(f: IFleet): IFleet = this.copy(fleetComponents = fleetComponents.:++(f.fleetComponents))
  
  def capacity: ICapacity =
    fleetComponents.map(_.capacity).foldLeft(Capacity())((x: ICapacity, y: ICapacity) => x.increase(y))

  override def description: String =
    s"""${name}
      |Firepower: ${firepower}
      |Size: ${units.length}
      |""".stripMargin

  override def split(): (IFleet, IFleet) =
    val (f1: Vector[IUnit], f2: Vector[IUnit]) = fleetComponents.splitAt(fleetComponents.length / 2)
    (Fleet(fleetComponents = f1), Fleet(fleetComponents = f2))


  override def extCopy(name: String, fleetComponents: Vector[IUnit], location: ICoordinate, moveVector: IMoveVector): IFleet =
    this.copy(name, fleetComponents, location, moveVector)