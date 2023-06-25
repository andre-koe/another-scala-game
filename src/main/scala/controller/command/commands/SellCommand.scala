package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.{IPurchasable, IUpkeep}
import model.game.gamestate.IGameStateManager
import model.game.gamestate.strategies.sell.{SellBuildingStrategy, SellUnitStrategy}

import scala.annotation.tailrec

case class SellCommand(what: IGameObject, qty: Int, where: ISector, gameStateManager: IGameStateManager) extends ICommand:

  override def execute(): IGameStateManager =
    where match
      case x: IPlayerSector =>
        what match
          case building: IBuilding => sellBuilding(building, qty, x)
          case unit: IUnit => sellUnit(unit, qty, x)
          case _ => gameStateManager.invalid(s"Cannot sell ${what.name}")
      case _ =>  gameStateManager.invalid("Specified sector is not a PlayerSector")

  private def sellUnit(str: IUnit, quantity: Int, location: IPlayerSector): IGameStateManager =
    gameStateManager.sell(SellUnitStrategy(location, Vector.fill(quantity)(str)))

  private def sellBuilding(str: IBuilding, quantity: Int, location: IPlayerSector): IGameStateManager =
    gameStateManager.sell(SellBuildingStrategy(location, str, quantity))
