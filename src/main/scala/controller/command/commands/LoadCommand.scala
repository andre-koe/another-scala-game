package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class LoadCommand(string: List[String], gameStateManager: GameStateManager) extends ICommand:
  override def execute(): GameStateManager = gameStateManager.load(Option(string.head))
