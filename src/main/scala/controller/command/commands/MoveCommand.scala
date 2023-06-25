package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.board.sector.ISector
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.mechanics.fleets.components.units.IUnit
import model.game.gamestate.IGameStateManager

case class MoveCommand(what: String, where: ICoordinate, gameStateManager: IGameStateManager) extends ICommand, IUndoable:

  override def execute(): IGameStateManager = gameStateManager.move(what, where)
