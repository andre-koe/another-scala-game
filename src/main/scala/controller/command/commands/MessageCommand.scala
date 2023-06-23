package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.messages.MessageType
import model.game.gamestate.enums.messages.MessageType._

import scala.io.AnsiColor

case class MessageCommand(message: String, messageType: MessageType, gameStateManager: IGameStateManager) extends ICommand:

  override def execute(): IGameStateManager =
    val msg = messageType match
      case INVALID_INPUT => invalid(message)
      case HELP => help(message)
      case MALFORMED_INPUT => malformed(message)
      case DEFAULT => message

    gameStateManager.message(msg)

  private def help(str: String) = AnsiColor.GREEN + str + AnsiColor.RESET

  private def invalid(str: String) = s"Invalid Input: ${AnsiColor.RED}'$str'${AnsiColor.RESET}" +
    s"\nEnter 'help' to get an overview of all available commands"

  private def malformed(str: String) = s"${AnsiColor.YELLOW}$str${AnsiColor.RESET}"
