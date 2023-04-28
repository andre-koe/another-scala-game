package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.SaveCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class SaveExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = SaveCommand(params, gsm)
