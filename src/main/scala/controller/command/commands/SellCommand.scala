package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.Capacity
import model.game.gamestate.IGameStateManager
import model.purchasable.building.{BuildingFactory, IBuilding}
import model.purchasable.units.{IUnit, UnitFactory}
import model.purchasable.utils.Output
import model.purchasable.{IGameObject, IUpkeep}
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

import scala.annotation.tailrec
import scala.language.postfixOps

case class SellCommand(string: String, gameStateManager: IGameStateManager) extends ICommand, IUndoable:
  private def capitalize(str: String): String = s"${str.split(" ").map(_.capitalize).mkString(" ")}"
  private def invalidInputFormat(str: String): String =
    s"'$str' is invalid expected input of type sell <what> (quantity) " +
      s"use sell help to get an overview on how to use sell."
  private def doesNotExistAsUnitOrBuilding(str: String): String =
    s"'${capitalize(str)}' is neither a Unit nor a Building, use 'list buildings|units' " +
      s"to get an overview of all available units or buildings."
  private def playerDoesNotOwnGameObject(str: String, quantity: Int): String =
    s"Cannot sell $quantity x ${capitalize(str)}" +
      s", you can only sell what you own."
  private def sellSuccessMsg(str: String, quantity: Int, profit: ResourceHolder): String =
    s"Successfully Sold: $quantity x ${capitalize(str)} for a profit of $profit."
  override def toString: String =
    "sell <name> (quantity) - " +
      "Sell a building or an unit by specifying it's name use the optional parameter " +
      "quantity to sell more than 1 instance. You get half the cost back."
  override def execute(): IGameStateManager =
    val inputAsList = string.toLowerCase.split(" ").toList
    if !validInputLength(inputAsList) then return gameStateManager.message(invalidInputFormat(string))
    inputAsList match
      case "help" :: Nil => gameStateManager.message(this.toString)
      case name :: Nil if name.nonEmpty => processInput(name, None)
      case name :: tail if name.nonEmpty => processInput(name, Option(tail))
      case _ => gameStateManager.message(this.toString)
  private def processInput(str: String, list: Option[List[String]]): IGameStateManager =
    list match
      case Some(l) => if l.length == 1 then handleListOfSizeOne(str, l) else handleListOfSizeTwo(str, l)
      case None => handleEmptyList(str)
  private def handleEmptyList(str: String): IGameStateManager =
    if gameObjectCanBeSold(str)
    then handleSell(str, 1) else gameStateManager.message(doesNotExistAsUnitOrBuilding(str))
  private def handleListOfSizeOne(str: String, value: List[String]): IGameStateManager =
    value.head.toIntOption match
      case Some(quantity) if gameObjectCanBeSold(str) => handleSell(str, quantity)
      case None if gameObjectCanBeSold(str + " " + value.head) =>
        handleSell(str + " " + value.head, 1)
      case _ => gameStateManager.message(doesNotExistAsUnitOrBuilding(str))
  private def handleListOfSizeTwo(str: String, value: List[String]): IGameStateManager =
    value.last.toIntOption match
      case Some(quantity) if gameObjectCanBeSold(str + " " + value.head) => handleSell(str + " " + value.head, quantity)
      case _ =>
        gameStateManager.message(doesNotExistAsUnitOrBuilding(str + " " + value.head))
  private def handleSell(what: String, quantity: Int): IGameStateManager =
    if playerHasUnit(what, quantity) then sellUnit(what, quantity)
    else if playerHasBuilding(what, quantity) then sellBuilding(what, quantity)
    else gameStateManager.message(playerDoesNotOwnGameObject(what, quantity))
  private def sellUnit(str: String, quantity: Int): IGameStateManager =
    val tupleUnit = removeFromUnitList(gameStateManager.playerValues.listOfUnits, quantity, str)
    val upkeepSaved = returnUpkeep(tupleUnit._2)
    val capacitySaved = returnSavedCapacity(tupleUnit._2)
      gameStateManager.sell(
      newUnits = Option(tupleUnit._1),
      newBuildings = None,
      profit = calcProfit(tupleUnit._2),
      capacity = capacitySaved,
      savedUpkeep = upkeepSaved,
      message = sellSuccessMsg(str, quantity, calcProfit(tupleUnit._2)))
  private def sellBuilding(str: String, quantity: Int): IGameStateManager =
    val tupleBuilding = removeFromBuildingList(gameStateManager.playerValues.listOfBuildings, quantity, str)
    val upkeepSaved = returnUpkeep(tupleBuilding._2)
    val outputAcc = returnOutput(tupleBuilding._2)
    gameStateManager.sell(
      newUnits = None,
      newBuildings = Option(tupleBuilding._1),
      profit = calcProfit(tupleBuilding._2),
      capacity = outputAcc.capacity,
      savedUpkeep = upkeepSaved,
      message = sellSuccessMsg(str, quantity, calcProfit(tupleBuilding._2)))
  private def calcProfit(list: List[IUpkeep]): ResourceHolder =
    if list.length > 1
    then list.map(_.cost).reduce((a, b) => a.increase(b)).divideBy(2)
    else list.map(_.cost).head.divideBy(2)
  private def removeFromBuildingList(lst: List[IBuilding], qty: Int, what: String): (List[IBuilding], List[IBuilding]) =
    val listFound: List[IBuilding] = lst.filter(_.name.toLowerCase == what)
    (lst diff listFound.drop(listFound.length - qty), listFound.drop(listFound.length - qty))
  private def removeFromUnitList(lst: List[IUnit], qty: Int, what: String): (List[IUnit], List[IUnit]) =
    val listFound: List[IUnit] = lst.filter(_.name.toLowerCase == what)
    (lst diff listFound.drop(listFound.length - qty), listFound.drop(listFound.length - qty))
  private def buildingExist(str: String): Option[IBuilding] = BuildingFactory().create(str)
  private def unitExist(str: String): Option[IUnit] = UnitFactory().create(str)
  private def playerHasUnit(str: String, quantity: Int): Boolean =
    gameStateManager.playerValues.listOfUnits.count(_.name.toLowerCase == str) >= quantity
  private def playerHasBuilding(str: String, quantity: Int): Boolean =
    gameStateManager.playerValues.listOfBuildings.count(_.name.toLowerCase == str) >= quantity
  private def gameObjectCanBeSold(str: String): Boolean =
    buildingExist(str.toLowerCase).isDefined || unitExist(str.toLowerCase).isDefined
  private def validInputLength(list: List[Any]): Boolean = if list.length > 3 | list.length < 1 then false else true
  private def returnUpkeep(list: List[IUpkeep]): ResourceHolder =
    if list.length > 1 then list.map(_.upkeep).reduce((a, b) => a.increase(b)) else list.map(_.upkeep).head
  private def returnOutput(list: List[IBuilding]): Output =
    list.map(_.output).reduce((a, b) => a.increaseOutput(b))
  private def returnSavedCapacity(list: List[IUnit]): Capacity =
    if list.length > 1 then list.map(_.capacity).reduce((a, b) => a.increase(b)) else list.map(_.capacity).head

