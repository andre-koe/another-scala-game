package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class EndGameCommand(gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.exit()

