package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.gamestate.IGameStateManager

case class UndoCommand(gameStateManager: IGameStateManager) extends ICommand:

  override def execute(): IGameStateManager = gameStateManager
