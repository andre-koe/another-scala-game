package model.core.board.sector.impl

import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.ICoordinate
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.Component
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{BuildSlots, IBuildSlots, SeqOperations}

import scala.xml.Elem

trait IPlayerSector extends ISector:

  def sector: ISector

  def constQuBuilding: Vector[IBuilding]

  def constQuUnits: Vector[IUnit]

  def buildingsInSector: Vector[IBuilding]

  def constructBuilding(building: IBuilding): IPlayerSector

  def constructUnit(rec: IUnit, qty: Int): IPlayerSector

  def removeUnits(rec: Vector[IUnit]): Option[IPlayerSector]

  override def buildSlots: IBuildSlots

  def updateSector(): IPlayerSector

  def extCopy(sector: ISector = sector,
              constQuBuilding: Vector[IBuilding] = constQuBuilding,
              constQuUnits: Vector[IUnit] = constQuUnits,
              buildingsInSector: Vector[IBuilding] = buildingsInSector): IPlayerSector

  override def toXML: Elem =
    <Sector>
      <Type>
        {"PlayerSector"}
      </Type>
      {sector.toXML}
      <ConstQuBuilding>
        {constQuBuilding.map(building => {building.toXML})}
      </ConstQuBuilding>
      <ConstQuUnits>
        {constQuUnits.map(unit => {unit.toXML})}
      </ConstQuUnits>
      <Buildings>
        {buildingsInSector.map(x => {x.toXML})}
      </Buildings>
    </Sector>