package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.EndGameCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class EndGameExpression() extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = EndGameCommand(gsm)
