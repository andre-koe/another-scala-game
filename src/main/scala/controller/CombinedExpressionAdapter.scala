package controller

import controller.command.ICommand
import controller.command.commands.InvalidCommand
import controller.newInterpreter.CombinedExpression
import controller.validator.ValidationHandler
import model.game.gamestate.GameStateManager

case class CombinedExpressionAdapter(cExpr: CombinedExpression, gsm: GameStateManager) extends ICommand:

  override def execute(): GameStateManager =
    ValidationHandler(gsm).handle(cExpr).get.execute()
