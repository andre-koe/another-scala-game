package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.MoveCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class MoveExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:
  override def interpret(gsm: GameStateManager): ICommand = MoveCommand(params, gsm) 
