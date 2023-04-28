package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class SaveCommand(string: List[String], gameStateManager: GameStateManager) extends ICommand:
  override def execute(): GameStateManager = gameStateManager.save(Option(string.head))
