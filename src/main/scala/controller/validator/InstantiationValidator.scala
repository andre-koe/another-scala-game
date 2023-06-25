package controller.validator

import controller.command.ICommand
import controller.command.commands.*
import controller.newInterpreter.CommandType.{BUILD, RECRUIT, RESEARCH}
import controller.newInterpreter.*
import controller.validator.validatorutils.ValidatorUtils
import model.core.board.sector.ISector
import model.core.board.boardutils.ICoordinate
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.interfaces.{IPurchasable, IUpkeep}
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.messages.MessageType.{HELP, MALFORMED_INPUT}
import model.utils.GameObjectUtils

import scala.language.postfixOps

case class InstantiationValidator(orig: String, gsm: IGameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]] =
    val command = ValidatorUtils().findCommandFirst(input).get
    val gameObj = ValidatorUtils().findGameObjects(input)
    val subcommand = ValidatorUtils().findSubcommands(input)
    val unidentified = ValidatorUtils().findUnidentified(input)
    val coordinate = ValidatorUtils().findCoordinate(input)
    val qty = ValidatorUtils().findQuantity(input)

    val returns = (gameObj, subcommand, unidentified) match
      case (None, None, None) => MessageCommand(helpCommand(command), MALFORMED_INPUT, gsm)
      case (None, None, Some(value)) => MessageCommand(helpObject(command, value.head), HELP, gsm)
      case (Some(value), None, None) => instantiationHandler(command, value, qty, coordinate)
      case (None, Some(value), None) => subcommandHandler(command, value)
      case _ => InvalidCommand(orig, gsm)

    Right(Some(returns))

  private def instantiationHandler(c: CommandType, go: Vector[IGameObject],
                                   i: Int, co: Option[ICoordinate]): ICommand =
    go.head match
      case x: IUnit if c == RECRUIT  => commandHandler(x, i, co, gsm)
      case x: IBuilding if c == BUILD => commandHandler(obj = x, coord = co, gsm = gsm)
      case x: ITechnology if c == RESEARCH => ResearchCommand(x, gsm)
      case x => MessageCommand(helpObject(c, x.name), MALFORMED_INPUT, gsm)

  private def commandHandler(obj: IUpkeep, qty: Int = 1, coord: Option[ICoordinate], gsm: IGameStateManager) : ICommand =
    val location = mapCoordToSector(coord)
    location match
      case Some(x) =>
        obj match
          case o: IUnit => RecruitCommand(o, qty, x, gsm)
          case o: IBuilding => BuildCommand(o, x, gsm)
          case _ => MessageCommand(helpLocation(CommandType.BUILD, orig), MALFORMED_INPUT, gsm)
      case _ => MessageCommand(helpLocation(CommandType.BUILD, orig), MALFORMED_INPUT, gsm)

  private def subcommandHandler(c: CommandType, sc: Vector[String]): ICommand =
    sc.head match
      case x: String if x matches ".*help.*" => MessageCommand(helpCommand(c), HELP, gsm)
      case _ => InvalidCommand(orig, gsm)

  private def mapCoordToSector(coord: Option[ICoordinate]): Option[ISector] =
    coord match
      case Some(x) => gsm.gameMap.getSectorAtCoordinate(x)
      case _ => gsm.gameMap.getPlayerSectors(gsm.currentPlayerValues.affiliation).headOption

  private def helpCommand(commandType: CommandType): String =
    commandType match
      case RECRUIT => "recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
        " - Enter list units for an overview of all available units"
      case BUILD  => "build <building name> - Enter list buildings for an overview of all available buildings."
      case RESEARCH => "research <technology name> - Enter 'list technologies' " +
        "for an overview of all available technologies."
      case _ => ""

  private def helpObject(commandType: CommandType, go: String): String =
    commandType match
      case BUILD => s"A building with name '$go' does not exist, " +
        s"use 'list building' to get an overview of all available buildings."
      case RECRUIT => s"A unit with name '$go' does not exist," +
        s" use 'list units' to get an overview of all available units."
      case RESEARCH => s"A technology with name '$go' does not exist," +
        s" use 'list tech' to get an overview of all available technologies."
      case _ => ""

  private def helpLocation(commandType: CommandType, go: String): String =
    commandType match
      case BUILD | RECRUIT => s"Invalid location specified! '$go' Lacking BuildSlots?"
      case RESEARCH => s"location is not applicable to 'research' use 'research help' to list all available options."
      case _ => ""
