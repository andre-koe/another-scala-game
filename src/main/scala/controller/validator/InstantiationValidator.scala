package controller.validator

import controller.command.ICommand
import controller.command.commands.*
import controller.newInterpreter.CommandType.{BUILD, RECRUIT, RESEARCH}
import controller.newInterpreter.*
import controller.validator.utils.ValidatorUtils
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.messages.MessageType.{HELP, MALFORMED_INPUT}
import model.game.purchasable.IGameObject
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.utils.GameObjectUtils

case class InstantiationValidator(orig: String, gsm: GameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedExpression]): Either[IValidator, Option[ICommand]] =
    val command = ValidatorUtils().findCommandFirst(input).get
    val gameObj = ValidatorUtils().findGameObjects(input)
    val subcommand = ValidatorUtils().findSubcommands(input)
    val unidentified = ValidatorUtils().findUnidentified(input)
    val qty = ValidatorUtils().findQuantity(input)

    val returns = (gameObj, subcommand, unidentified) match
      case (None, None, None) => MessageCommand(helpCommand(command), MALFORMED_INPUT, gsm)
      case (None, None, Some(value)) => MessageCommand(helpObject(command, value.head), HELP, gsm)
      case (Some(value), None, None) => instantiationHandler(command, value, qty)
      case (None, Some(value), None) => subcommandHandler(command, value)
      case _ => InvalidCommand(orig, gsm)

    Right(Some(returns))

  private def instantiationHandler(c: CommandType, go: Vector[IGameObject], i: Int): ICommand =
    go.head match
      case x: IUnit if c == RECRUIT  => RecruitCommand(x, i, gsm)
      case x: IBuilding if c == BUILD => BuildCommand(x, gsm)
      case x: ITechnology if c == RESEARCH => ResearchCommand(x, gsm)
      case x => MessageCommand(helpObject(c, x.name), MALFORMED_INPUT, gsm)

  private def subcommandHandler(c: CommandType, sc: Vector[String]): ICommand =
    sc.head match
      case x: String if x matches ".*help.*" => MessageCommand(helpCommand(c), HELP, gsm)
      case _ => InvalidCommand(orig, gsm)

  private def helpCommand(commandType: CommandType): String =
    commandType match
      case RECRUIT => "recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
        " - Enter list units for an overview of all available units"
      case BUILD  => "build <building name> - Enter list buildings for an overview of all available buildings."
      case RESEARCH => "research <technology name> - Enter 'list technologies' " +
        "for an overview of all available technologies."

  private def helpObject(commandType: CommandType, go: String): String =
    commandType match
      case BUILD => s"A building with name '$go' does not exist, " +
        s"use 'list building' to get an overview of all available buildings."
      case RECRUIT => s"A unit with name '$go' does not exist," +
        s" use 'list units' to get an overview of all available units."
      case RESEARCH => s"A technology with name '$go' does not exist," +
        s" use 'list tech' to get an overview of all available technologies."
