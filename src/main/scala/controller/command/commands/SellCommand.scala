package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.Capacity
import model.game.gamestate.GameStateManager
import model.game.purchasable.building.{BuildingFactory, IBuilding}
import model.game.purchasable.units.{IUnit, UnitFactory}
import model.game.purchasable.utils.Output
import model.game.purchasable.{IGameObject, IUpkeep}
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}

import scala.annotation.tailrec

case class SellCommand(what: IGameObject, qty: Int, gameStateManager: GameStateManager) extends ICommand, IUndoable:

  private def capitalize(str: String): String = s"${str.split(" ").map(_.capitalize).mkString(" ")}"

  private def sellSuccessMsg(str: String, quantity: Int, profit: ResourceHolder): String =
    s"Successfully Sold: $quantity x ${capitalize(str)} for a profit of $profit."

  override def toString: String =
    "sell <name> (quantity) - " +
      "Sell a building or an unit by specifying it's name use the optional parameter " +
      "quantity to sell more than 1 instance. You get half the cost back."

  override def execute(): GameStateManager =
    what match
      case building: IBuilding => sellBuilding(building, qty)
      case unit: IUnit => sellUnit(unit, qty)

  private def sellUnit(str: IUnit, quantity: Int): GameStateManager =
    val tupleUnit = removeFromUnitList(gameStateManager.playerValues.listOfUnits, quantity, str)
    val capacitySaved = returnSavedCapacity(tupleUnit._2)
      gameStateManager.sell(
      newUnits = tupleUnit._1,
      newBuildings = List(),
      profit = calcProfit(tupleUnit._2),
      capacity = capacitySaved,
      message = sellSuccessMsg(str.name, quantity, calcProfit(tupleUnit._2)))

  private def sellBuilding(str: IBuilding, quantity: Int): GameStateManager =
    val tupleBuilding = removeFromBuildingList(gameStateManager.playerValues.listOfBuildings, quantity, str)
    val outputAcc = returnOutput(tupleBuilding._2)
    gameStateManager.sell(
      newUnits = List(),
      newBuildings = tupleBuilding._1,
      profit = calcProfit(tupleBuilding._2),
      capacity = outputAcc.capacity,
      message = sellSuccessMsg(str.name, quantity, calcProfit(tupleBuilding._2)))

  private def calcProfit(list: List[IUpkeep]): ResourceHolder =
    if list.length > 1
    then list.map(_.cost).reduce((a, b) => a.increase(b)).divideBy(2)
    else list.map(_.cost).head.divideBy(2)

  private def removeFromBuildingList(lst: List[IBuilding], qty: Int, what: IBuilding): (List[IBuilding], List[IBuilding]) =
    val listFound: List[IBuilding] = lst.filter(_ == what)
    (lst diff listFound.drop(listFound.length - qty), listFound.drop(listFound.length - qty))

  private def removeFromUnitList(lst: List[IUnit], qty: Int, what: IUnit): (List[IUnit], List[IUnit]) =
    val listFound: List[IUnit] = lst.filter(_ == what)
    (lst diff listFound.drop(listFound.length - qty), listFound.drop(listFound.length - qty))

  private def returnOutput(list: List[IBuilding]): Output =
    list.map(_.output).reduce((a, b) => a.increaseOutput(b))

  private def returnSavedCapacity(list: List[IUnit]): Capacity =
    if list.length > 1 then list.map(_.capacity).reduce((a, b) => a.increase(b)) else list.map(_.capacity).head