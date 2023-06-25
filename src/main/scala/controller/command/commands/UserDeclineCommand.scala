package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class UserDeclineCommand(gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.decline()
