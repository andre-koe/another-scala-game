package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, MessageCommand, ShowCommand}
import model.game.gamestate.GameStateManager
import model.game.gamestate.messages.MessageType
import model.game.gamestate.messages.MessageType.{HELP, INVALID_INPUT}
import model.interpreter.IExpression

case class ShowExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:
  override def interpret(gsm: GameStateManager): ICommand =
    params match
      case "overview" :: Nil => ShowCommand(gsm)
      case ("" | "help") :: Nil => MessageCommand(showHelp, HELP, gsm)
      case _ => MessageCommand(params.mkString(" "), INVALID_INPUT, gsm)
  override def showHelp: String = "show overview - " +
    "display the current player stats, buildings, tech, units, income, etc."
