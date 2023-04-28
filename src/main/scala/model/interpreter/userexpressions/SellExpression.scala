package model.interpreter.userexpressions

import controller.command.ICommand
import controller.command.commands.{MessageCommand, SellCommand}
import model.game.gamestate.GameStateManager
import model.game.gamestate.messages.MessageType
import model.game.gamestate.messages.MessageType.{HELP, INVALID_INPUT, MALFORMED_INPUT}
import model.game.purchasable.IGameObject
import model.game.purchasable.building.BuildingFactory
import model.game.purchasable.technology.{ITechnology, TechnologyFactory}
import model.game.purchasable.units.UnitFactory
import model.interpreter.IExpression
import model.utils.GameObjectUtils

case class SellExpression(params: List[String]) extends IExpression[GameStateManager, ICommand]:

  override def interpret(gsm: GameStateManager): ICommand =
    params match
      case ("help" | "") :: Nil => MessageCommand(helpSell, HELP, gsm)
      case _ => validateInput(params, gsm)

  private def validateInput(params: List[String], gsm: GameStateManager): ICommand =
    params match
      case value :: Nil => validateListOfSizeOne(value, gsm)
      case value :: quantity :: Nil => validateListOfSizeTwo(value, quantity, gsm)
      case _ => MessageCommand(invalidInputFormat(params.mkString(" ")), INVALID_INPUT, gsm)

  private def validateListOfSizeOne(str: String, gsm: GameStateManager): ICommand =
    GameObjectUtils().findInLists(str).get match
      case someTech: ITechnology => MessageCommand(cantBeSold(someTech.name), MALFORMED_INPUT, gsm)

  private def processInput(str: String, list: Option[List[String]]): GameStateManager =
    list match
      case Some(l) => if l.length == 1 then handleListOfSizeOne(str, l) else handleListOfSizeTwo(str, l)
      case None => handleEmptyList(str)

  private def handleEmptyList(str: String): GameStateManager =
    if gameObjectCanBeSold(str)
    then handleSell(str, 1) else gameStateManager.message(doesNotExistAsUnitOrBuilding(str))

  private def handleListOfSizeOne(str: String, value: List[String]): GameStateManager =
    value.head.toIntOption match
      case Some(quantity) if gameObjectCanBeSold(str) => handleSell(str, quantity)
      case None if gameObjectCanBeSold(str + " " + value.head) =>
        handleSell(str + " " + value.head, 1)
      case _ => gameStateManager.message(doesNotExistAsUnitOrBuilding(str))

  private def handleListOfSizeTwo(str: String, value: List[String]): GameStateManager =
    value.last.toIntOption match
      case Some(quantity) if gameObjectCanBeSold(str + " " + value.head) => handleSell(str + " " + value.head, quantity)
      case _ =>
        gameStateManager.message(doesNotExistAsUnitOrBuilding(str + " " + value.head))

  private def gameObjectCanBeSold(str: String): Boolean =
    buildingExist(str.toLowerCase).isDefined || unitExist(str.toLowerCase).isDefined

  private def cantBeSold(str: String): String =
    s"$str can't be sold - You can only sell Units and Buildings you own!"

  private def invalidInputFormat(str: String): String =
    s"Expected input of type sell <what> (quantity) but found '$str'" +
      s"enter 'sell help' to get an overview on how to use sell."

  private def helpSell: String = "sell <name> (quantity) - " +
    "Sell a building or an unit by specifying it's name use the optional parameter " +
    "quantity to sell more than 1 instance. You get half the cost back."