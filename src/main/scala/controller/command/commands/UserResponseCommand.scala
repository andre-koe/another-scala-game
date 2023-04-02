package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class UserResponseCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager = 
    if string matches "(y|Y).*" then gameStateManager.endRoundConfirmation() else gameStateManager.resetGameState()
    
