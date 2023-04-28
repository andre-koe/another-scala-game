package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.InvalidCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class InvalidExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:
  override def interpret(gsm: GameStateManager): ICommand = InvalidCommand(params.mkString(" "), gsm)