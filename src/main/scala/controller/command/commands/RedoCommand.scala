package controller.command.commands

import model.game.gamestate.IGameStateManager
import controller.command.ICommand

case class RedoCommand(gameStateManager: IGameStateManager) extends ICommand:
  
  override def execute(): IGameStateManager = gameStateManager
