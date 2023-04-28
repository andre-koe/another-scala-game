package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.LoadCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class LoadExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = LoadCommand(params, gsm) 
