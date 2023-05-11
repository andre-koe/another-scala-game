package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class LoadCommand(string: Option[String], gameStateManager: GameStateManager) extends ICommand:

  override def execute(): GameStateManager = gameStateManager.load(string)
