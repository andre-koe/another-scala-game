package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.resources.resourcetypes.{Energy, ResearchPoints}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.mechanics.fleets.components.{Component, units}
import model.core.utilities.interfaces.{IPurchasable, IUpkeep}
import model.core.utilities.{Capacity, ResourceHolder}
import model.game.gamestate.IGameStateManager
import model.game.gamestate.strategies.sell.{SellBuildingStrategy, SellUnitStrategy}

import scala.annotation.tailrec

case class SellCommand(what: IGameObject, qty: Int, where: ISector, gameStateManager: IGameStateManager)
  extends ICommand, IUndoable:

  override def execute(): IGameStateManager =
    what match
      case building: IBuilding => sellBuilding(building, qty, where.asInstanceOf[IPlayerSector])
      case unit: IUnit => sellUnit(unit, qty, where.asInstanceOf[IPlayerSector])
      case _ => gameStateManager.invalid(s"Cannot sell ${what.name}")

  private def sellUnit(str: IUnit, quantity: Int, location: IPlayerSector): IGameStateManager =
    gameStateManager.sell(SellUnitStrategy(location, Vector.fill(quantity)(str)))

  private def sellBuilding(str: IBuilding, quantity: Int, location: IPlayerSector): IGameStateManager =
    gameStateManager.sell(SellBuildingStrategy(location, str, quantity))
