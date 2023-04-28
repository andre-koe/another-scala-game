package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.EndRoundCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class EndRoundExpression() extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = EndRoundCommand(gsm);
