package controller.validator

import controller.command.ICommand
import controller.newInterpreter.InterpretedInput

trait IValidator:
  def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]]
