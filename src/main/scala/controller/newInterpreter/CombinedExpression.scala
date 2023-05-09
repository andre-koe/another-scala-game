package controller.newInterpreter

import controller.command.ICommand
import controller.command.commands.InvalidCommand
import controller.validator.ValidationHandler
import model.game.gamestate.GameStateManager

case class CombinedExpression(input: Vector[InterpretedExpression], orig: String)