package controller.validator

import controller.command.ICommand
import controller.newInterpreter.InterpretedExpression
import model.game.gamestate.GameStateManager

trait IValidator:
  def validate(input: Vector[InterpretedExpression]): Either[IValidator, Option[ICommand]]
