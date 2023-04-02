package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.gamestate.IGameStateManager

case class DummyCommand(gameStateManager: IGameStateManager) extends ICommand, IUndoable:
  override def execute(): IGameStateManager = gameStateManager
