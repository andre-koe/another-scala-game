package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, ListCommand, MessageCommand}
import controller.newInterpreter.InterpretedInputToken
import controller.validator.utils.ValidatorUtils
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.ListParams.*
import model.game.gamestate.enums.messages.MessageType.*

case class ListValidator(orig: String, gsm: GameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedInputToken]): Either[IValidator, Option[ICommand]] =
    Right(Option(subCommandInterpreter(ValidatorUtils().findSubcommands(input))))

  private def subCommandInterpreter(commands: Option[Vector[String]]): ICommand =
    commands match
      case Some(x) => handleConcreteSubcommand(x)
      case _ => ListCommand(ALL, gsm)

  private def handleConcreteSubcommand(commands: Vector[String]): ICommand =
    if commands.length > 1 then InvalidCommand(orig, gsm)
    else commands.head match
      case x if x matches ".*tech.*" => ListCommand(TECHNOLOGY, gsm)
      case x if x matches ".*building.*" => ListCommand(BUILDING, gsm)
      case x if x matches ".*unit.*" => ListCommand(UNITS, gsm)
      case x if x matches ".*all.*" => ListCommand(ALL, gsm)
      case x if x matches ".*help.*" => MessageCommand(help, HELP, gsm)
      case _ => MessageCommand(unknownInput(commands.head), MALFORMED_INPUT, gsm)

  private def help: String =
    "The list command with optional parameter (units/buildings/technologies) will list all available Game Objects " +
      "according to input, if omitted everything will be listed.\nEnter list help to see this message"

  private def unknownInput(str: String): String =
    s"'$str' is not a valid parameter for 'list' enter 'help list' to get an overview on how to use 'list'"
