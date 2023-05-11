package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class UserAcceptCommand(gsm: GameStateManager) extends ICommand:

  override def execute(): GameStateManager = gsm.accept()
