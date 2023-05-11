package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.messages.MessageType

case class ShowCommand(gsm: GameStateManager) extends ICommand:
  
  override def execute(): GameStateManager = gsm.show()
