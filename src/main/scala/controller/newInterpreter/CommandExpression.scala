package controller.newInterpreter
import controller.command.ICommand
import controller.newInterpreter.CommandType._
import model.game.gamestate.GameStateManager

case class CommandExpression(token: String) extends AbstractExpression[CommandType]:

  override def interpret(): CommandType =
    token match
      case "help" => HELP
      case "build" => BUILD
      case "recruit" => RECRUIT
      case "research" => RESEARCH
      case "load" => LOAD
      case "save" => SAVE
      case "quit" | "exit" => END_GAME
      case "done" => END_ROUND
      case "sell" => SELL
      case "show" => SHOW
      case "list" => LIST
      case "y" | "yes" => USER_ACCEPT
      case "n" | "no" => USER_DECLINE
      case "move" => MOVE
      case _ => INVALID


