package controller.validator

import controller.command.ICommand
import controller.newInterpreter.InterpretedInputToken
import model.game.gamestate.GameStateManager

trait IValidator:
  def validate(input: Vector[InterpretedInputToken]): Either[IValidator, Option[ICommand]]
