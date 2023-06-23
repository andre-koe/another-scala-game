package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class UserAcceptCommand(gameStateManager: IGameStateManager) extends ICommand:

  override def execute(): IGameStateManager = gameStateManager.accept()
