package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.ListCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class ListExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand =
    params match
      case value :: Nil => ListCommand(value, gsm)
      case _ => ListCommand("", gsm)

