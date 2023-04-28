package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.RecruitCommand
import model.game.gamestate.GameStateManager
import model.interpreter.IExpression

case class RecruitExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = RecruitCommand(params, gsm)
