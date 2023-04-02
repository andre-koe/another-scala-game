package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class InvalidCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = gameStateManager.invalid(s"Unknown Input: '$string'")
