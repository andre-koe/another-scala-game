package controller.validator

import controller.command.ICommand
import controller.command.commands.{HelpCommand, InvalidCommand, MessageCommand}
import controller.newInterpreter.{InterpretedCommand, InterpretedInput, InterpretedGameObject, InterpretedSubcommand}
import controller.validator.validatorutils.ValidatorUtils
import model.core.gameobjects.purchasable.IGameObject
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.help.HelpContext.{BUILDING, GENERAL, SPECIFIC, TECHNOLOGY, UNIT}
import model.game.gamestate.enums.messages.MessageType.HELP

case class HelpValidator(orig: String, gsm: IGameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]] =
    val unidentified = ValidatorUtils().findUnidentified(input)
    val obj = ValidatorUtils().findGameObjects(input)
    val string = ValidatorUtils().findSubcommands(input)

    val returns = (obj, string, unidentified) match
      case (None, None, None) => HelpCommand(GENERAL, gsm)
      case (None, None, Some(x)) => unspecificHelp(x)
      case (Some(x), None, None) => HelpCommand(SPECIFIC, gsm, x.headOption)
      case (None, Some(x), None) => unspecificHelp(x)
      case _ => InvalidCommand(orig, gsm)

    Right(Some(returns))

  private def unspecificHelp(str: Vector[String]): ICommand =
    str.head match
      case x if x matches ".*build.*" => HelpCommand(BUILDING, gsm)
      case x if x matches ".*tech.*" => HelpCommand(TECHNOLOGY, gsm)
      case x if x matches ".*recruit.*" => HelpCommand(UNIT, gsm)
      case x if x matches ".*help.*" => HelpCommand(GENERAL, gsm)
      case _ => MessageCommand(couldNotFind(str.head), HELP, gsm)

  private def couldNotFind(str: String): String = s"Could not find any information on '$str'"