package controller.validator

import controller.command.ICommand
import controller.command.commands.{MessageCommand, ShowCommand}
import controller.newInterpreter.{InterpretedInputToken, InterpretedSubcommand}
import controller.validator.utils.ValidatorUtils
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.messages.MessageType.{HELP, INVALID_INPUT}

case class ShowValidator(orig: String, gsm: GameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedInputToken]): Either[IValidator, Option[ICommand]] =
    val sc = ValidatorUtils().findSubcommands(input)
    val returns = sc match
      case Some(x) => validateSubcommands(x)
      case _ => MessageCommand(showHelp, HELP, gsm)

    Right(Some(returns))

  private def validateSubcommands(input: Vector[String]): ICommand =
    input match
      case Vector(x) if x matches ".*overview.*" => ShowCommand(gsm)
      case Vector(x) if x matches ".*help.*" => MessageCommand(showHelp, HELP, gsm)
      case Vector(_) => MessageCommand(orig, INVALID_INPUT, gsm)
      case _ => MessageCommand(orig, INVALID_INPUT, gsm)

  private def showHelp: String = "show overview - display the current player stats, buildings, tech, units, income, etc."