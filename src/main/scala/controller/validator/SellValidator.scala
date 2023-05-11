package controller.validator

import controller.command.ICommand
import controller.command.commands.{MessageCommand, SellCommand}
import controller.newInterpreter.{InterpretedExpression, InterpretedSubcommand}
import controller.validator.utils.ValidatorUtils
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.messages.MessageType.*
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.{IGameObject, IUpkeep}

case class SellValidator(orig: String, gsm: GameStateManager) extends IValidator:
  override def validate(input: Vector[InterpretedExpression]): Either[IValidator, Option[ICommand]] =
    val sc = ValidatorUtils().findSubcommands(input)
    val qt = ValidatorUtils().findQuantity(input)
    val go = ValidatorUtils().findGameObjects(input)
    val un = ValidatorUtils().findUnidentified(input)

    val returns = (go, sc, un) match
      case (None, None, None) => MessageCommand(helpSell, HELP, gsm)
      case (Some(x), None, None) => handleSell(x, qt)
      case (None, Some(x), None) => handleSubcommand(x)
      case (_, _, Some(x)) => handleUnidentified(x)
      case _ => MessageCommand(invalidInputFormat(orig), INVALID_INPUT, gsm)

    Right(Some(returns))

  private def handleSubcommand(params: Vector[String]):  ICommand =
    params match
      case Vector(x) if x matches ".*help.*" => MessageCommand(helpSell, HELP, gsm)
      case Vector(_) => MessageCommand(invalidInputFormat(orig), INVALID_INPUT, gsm)
      case _ => MessageCommand(invalidInputFormat(orig), INVALID_INPUT, gsm)

  private def handleSell(go: Vector[IGameObject], qty: Int): ICommand =
    go match
      case Vector(x) if x.isInstanceOf[IUpkeep] && playerHasEntity(x, qty) => SellCommand(x, qty, gsm)
      case Vector(x) if x.isInstanceOf[IUpkeep] => MessageCommand(notOwnedOrWrongQty(x.name, qty), MALFORMED_INPUT, gsm)
      case _ => MessageCommand(technologyCantBeSold(go.head.name), MALFORMED_INPUT, gsm)

  private def playerHasEntity(value: IGameObject, qty: Int): Boolean =
    gsm.playerValues.listOfBuildings.count(_.name == value.name) >= qty ||
      gsm.playerValues.listOfUnits.count(_.name == value.name) >= qty

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
