package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class EmptyCommand(gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.empty()
