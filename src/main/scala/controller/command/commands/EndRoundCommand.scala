package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class EndRoundCommand(gameStateManager: GameStateManager) extends ICommand:
  override def execute(): GameStateManager = gameStateManager.endRoundRequest()