package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager
import model.game.gamestate.messages.MessageType
import model.game.gamestate.messages.MessageType.{DEFAULT, HELP}

case class ShowCommand(gsm: GameStateManager) extends ICommand:
  
  override def execute(): GameStateManager = gsm.show()
