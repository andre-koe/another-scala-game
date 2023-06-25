package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.messages.MessageType

case class ShowCommand(gameStateManager: IGameStateManager) extends ICommand:
  
  override def execute(): IGameStateManager = gameStateManager.show()
