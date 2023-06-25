package controller.adapters

import controller.command.ICommand
import controller.command.commands.InvalidCommand
import controller.newInterpreter.TokenizedInput
import controller.validator.ValidationHandler
import model.game.gamestate.IGameStateManager

case class TokenizedInputToCommandAdapter(cExpr: TokenizedInput, gameStateManager: IGameStateManager) extends ICommand:

  def getCommand: ICommand =
    ValidationHandler(gameStateManager).handle(cExpr).get

  override def execute(): IGameStateManager =
    ValidationHandler(gameStateManager).handle(cExpr).get.execute()
