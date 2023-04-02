package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.Coordinate
import model.game.gamestate.IGameStateManager

case class MoveCommand(string: String, gameStateManager: IGameStateManager) extends ICommand, IUndoable:
  override def execute(): IGameStateManager = gameStateManager.move(string, Coordinate(1,2))