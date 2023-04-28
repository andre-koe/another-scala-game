package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.ResearchCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class ResearchExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = ResearchCommand(params, gsm)
