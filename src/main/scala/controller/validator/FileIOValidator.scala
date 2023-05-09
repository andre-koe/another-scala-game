package controller.validator
import controller.command.ICommand
import controller.validator.IValidator
import controller.command.commands.{InvalidCommand, LoadCommand, MessageCommand, SaveCommand}
import controller.newInterpreter.{CommandType, InterpretedCommand, InterpretedExpression, InterpretedSubcommand}
import controller.newInterpreter.CommandType.{LOAD, SAVE}
import controller.validator.utils.ValidatorUtils
import model.game.gamestate.enums.messages.MessageType
import model.game.gamestate.enums.messages.MessageType._
import model.game.gamestate.GameStateManager

case class FileIOValidator(orig: String, gsm: GameStateManager) extends IValidator:

  override def validate(input: Vector[InterpretedExpression]): Either[IValidator, Option[ICommand]] =
    val command = ValidatorUtils().findCommandFirst(input)

    val subcommand = ValidatorUtils().findSubcommands(input) match
      case Some(value) => value
      case None => Vector()

    Right(Some(handle(command.get, subcommand)))

  private def handle(ct: CommandType, commands: Vector[String]): ICommand =
    commands match
      case Vector(x) if x matches ".*f=.+" => fileIOHandler(ct, x.split("f=").last)
      case Vector(x) if x matches ".*help.*" => MessageCommand(helpHandler(ct), HELP, gsm)
      case Vector() => MessageCommand(helpHandler(ct), HELP, gsm)
      case _ => MessageCommand(invalidInput(ct), MALFORMED_INPUT, gsm)

  private def helpHandler(ct: CommandType): String =
    ct match
      case SAVE => "The Save command requires one argument -f=<filename> and will store the current GameState"
      case _ => "The Load command requires one argument -f=<filename> and will load a previously saved GameState"

  private def invalidInput(ct: CommandType, str: String = orig): String =
    ct match
      case SAVE => s"Malformed Input for 'save' expected 'save -f=<yourfilename>' but found $str"
      case _ => s"Malformed Input for 'load' expected 'load -f=<yourfilename>' but found $str"

  private def fileIOHandler(ct: CommandType, str: String): ICommand =
    ct match
      case SAVE => SaveCommand(Some(str), gsm)
      case _ => LoadCommand(Some(str), gsm)