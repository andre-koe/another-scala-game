package controller.validator

import controller.command.ICommand
import controller.command.commands.{MessageCommand, SellCommand}
import controller.newInterpreter.{InterpretedInput, InterpretedSubcommand}
import controller.validator.validatorutils.ValidatorUtils
import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector, Sector}
import model.core.board.boardutils.{ICoordinate, Coordinate}
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.utilities.interfaces.IUpkeep
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.messages.MessageType.*

case class SellValidator(orig: String, gsm: IGameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]] =
    val sc = ValidatorUtils().findSubcommands(input)
    val qt = ValidatorUtils().findQuantity(input)
    val go = ValidatorUtils().findGameObjects(input)
    val un = ValidatorUtils().findUnidentified(input)
    val coord = ValidatorUtils().findCoordinate(input)

    val returns = (go, sc, un) match
      case (None, None, None) => MessageCommand(helpSell, HELP, gsm)
      case (Some(x), None, None) => handleSell(x, qt, coord)
      case (None, Some(x), None) => handleSubcommand(x)
      case (_, _, Some(x)) => handleUnidentified(x)
      case _ => MessageCommand(invalidInputFormat(orig), INVALID_INPUT, gsm)

    Right(Some(returns))

  private def handleSubcommand(params: Vector[String]):  ICommand =
    params match
      case Vector(x) if x matches ".*help.*" => MessageCommand(helpSell, HELP, gsm)
      case Vector(_) => MessageCommand(invalidInputFormat(orig), INVALID_INPUT, gsm)
      case _ => MessageCommand(invalidInputFormat(orig), INVALID_INPUT, gsm)

  private def handleSell(go: Vector[IGameObject], qty: Int, location: Option[ICoordinate]): ICommand =
    go match
      case Vector(x) if x.isInstanceOf[IUpkeep] && playerHasEntity(x, qty, location) =>
        SellCommand(x, qty, gsm.gameMap.getSectorAtCoordinate(location.get).get, gsm)
      case Vector(x) if x.isInstanceOf[IUpkeep] =>
        MessageCommand(notOwnedOrWrongQty(x.name, qty), MALFORMED_INPUT, gsm)
      case _ =>
        MessageCommand(technologyCantBeSold(go.head.name), MALFORMED_INPUT, gsm)

  private def playerHasEntity(value: IGameObject, qty: Int, location: Option[ICoordinate]): Boolean =
    map2Sector(location) match
      case Some(x) => x.unitsInSector.flatMap(_.fleetComponents).count(_.name == value.name) >= qty ||
          x.buildingsInSector.count(_.name == value.name) >= qty
      case _ => false

  private def map2Sector(location: Option[ICoordinate]) : Option[IPlayerSector] =
    gsm.gameMap.getSectorAtCoordinate(location.getOrElse(Coordinate(-1,-1))) match
      case Some(x) => x match
        case x : IPlayerSector => Some(x)
        case _ => None
      case _ => None

  private def handleUnidentified(value: Vector[String]): ICommand =
    MessageCommand(cantBeSold(value.head), MALFORMED_INPUT, gsm)
  private def technologyCantBeSold(str: String): String =
    s"'$str' is a Technology you can only sell objects of type 'Unit' or 'Building'"

  private def cantBeSold(str: String = "Object"): String =
    s"$str can't be sold - You can only sell Units and Buildings you own!"

  private def notOwnedOrWrongQty(what: String, qty: Int): String =
    s"Can't sell $qty x $what! Wrong quantity?"

  private def invalidInputFormat(str: String): String =
    s"Expected input of type sell <what> (quantity) but found '$str'" +
      s"enter 'sell help' to get an overview on how to use sell."

  private def helpSell: String = "sell <name> (quantity) - " +
    "Sell a building or an unit by specifying it's name use the optional parameter " +
    "quantity to sell more than 1 instance. You get half the cost back."
