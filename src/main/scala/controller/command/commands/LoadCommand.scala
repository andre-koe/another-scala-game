package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class LoadCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.load(Option(string))
