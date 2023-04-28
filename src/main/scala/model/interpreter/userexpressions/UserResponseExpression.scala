package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.{MessageCommand, UserAcceptCommand, UserDeclineCommand}
import model.game.gamestate.GameStateManager
import model.game.gamestate.messages.MessageType.INVALID_INPUT
import model.interpreter.IExpression

case class UserResponseExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand = 
    params match
      case accepted :: Nil if accepted matches "y*" => UserAcceptCommand(gsm)
      case declined :: Nil if declined matches "n*" => UserDeclineCommand(gsm)
      case _ => MessageCommand(params.mkString(" "), INVALID_INPUT, gsm)