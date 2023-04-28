package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.{MessageCommand, SellCommand}
import model.game.gamestate.GameStateManager
import model.game.gamestate.messages.MessageType
import model.game.gamestate.messages.MessageType.{HELP, INVALID_INPUT, MALFORMED_INPUT}
import model.game.purchasable.{IGameObject, IUpkeep}
import model.game.purchasable.building.IBuilding
import model.game.purchasable.units.IUnit
import model.interpreter.IExpression
import model.utils.GameObjectUtils

case class SellExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand =
    params match
      case ("help" | "") :: Nil => MessageCommand(helpSell, HELP, gsm)
      case _ => validateInput(params, gsm)

  private def validateInput(params: List[String], gsm: GameStateManager): ICommand =
    params match
      case value :: Nil => validateList(str = value, gsm = gsm)
      case value :: tail => validateListOfSizeTwo(value, tail, gsm)
      case _ => MessageCommand(invalidInputFormat(params.mkString(" ")), INVALID_INPUT, gsm)

  private def validateList(str: String, quantity: Int = 1, gsm: GameStateManager): ICommand =
    val found = GameObjectUtils().findInLists(str)
    if found.isDefined then
      found.get match
      case value: IUpkeep if playerHasEntity(value, gsm) => SellCommand(value, quantity, gsm)
      case _ => MessageCommand(technologyCantBeSold(found.get.name), MALFORMED_INPUT, gsm)
    else MessageCommand(cantBeSold(str), MALFORMED_INPUT, gsm)

  private def playerHasEntity(value: IUpkeep, gsm: GameStateManager): Boolean =
    gsm.playerValues.listOfBuildings.exists(_.name == value.name) ||
      gsm.playerValues.listOfUnits.exists(_.name == value.name)

  private def validateListOfSizeTwo(str: String, tail: List[String], gsm: GameStateManager): ICommand =
   tail match
     case value :: Nil =>
       val qty = value.toIntOption
       if qty.isDefined then validateList(str, qty.get, gsm)
       else validateList(str = str + " " + value, gsm = gsm)
     case value :: quantity :: Nil =>
       val qty = quantity.toIntOption
       if qty.isDefined then validateList(str + " " + value, qty.get, gsm)
       else MessageCommand(invalidInputFormat(str + tail.mkString(" ")), INVALID_INPUT, gsm)
     case _ => MessageCommand(invalidInputFormat(str + tail.mkString(" ")), INVALID_INPUT, gsm)

  private def technologyCantBeSold(str: String): String =
    s"'$str' is a Technology you can only sell objects of type 'Unit' or 'Building'"

  private def cantBeSold(str: String): String =
    s"$str can't be sold - You can only sell Units and Buildings you own!"

  private def invalidInputFormat(str: String): String =
    s"Expected input of type sell <what> (quantity) but found '$str'" +
      s"enter 'sell help' to get an overview on how to use sell."

  private def helpSell: String = "sell <name> (quantity) - " +
    "Sell a building or an unit by specifying it's name use the optional parameter " +
    "quantity to sell more than 1 instance. You get half the cost back."