package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.HelpCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class HelpExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:
  override def interpret(gsm: GameStateManager): ICommand = HelpCommand(params.head, gsm)
