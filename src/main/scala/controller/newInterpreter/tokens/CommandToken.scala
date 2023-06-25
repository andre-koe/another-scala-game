package controller.newInterpreter.tokens

import controller.command.ICommand
import controller.newInterpreter.CommandType.*
import controller.newInterpreter.tokens.AbstractToken
import controller.newInterpreter.CommandType

case class CommandToken(token: String) extends AbstractToken[CommandType]:

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
      case "undo" => UNDO
      case "redo" => REDO
      case _ => INVALID


