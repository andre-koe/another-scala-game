package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.BuildCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class BuildExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:
  
  override def interpret(gsm: GameStateManager): ICommand = BuildCommand(params, gsm)

