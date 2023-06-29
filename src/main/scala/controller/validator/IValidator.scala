package controller.validator

import controller.command.ICommand
import controller.newInterpreter.InterpretedInput

/**
 * Takes an tokenized Input and ultimately returns an ICommand
 */
trait IValidator:
  /**
   *
   * @param input (tokenized)
   * @return either a more specialised IValidator Instance or an Option(Command)
   */
  def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]]
