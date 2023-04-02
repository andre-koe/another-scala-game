package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class SaveCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.save(Option(string))
