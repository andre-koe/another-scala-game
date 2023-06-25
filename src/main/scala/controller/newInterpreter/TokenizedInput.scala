package controller.newInterpreter

import controller.command.ICommand
import controller.command.commands.InvalidCommand
import controller.validator.ValidationHandler

case class TokenizedInput(input: Vector[InterpretedInput], orig: String)