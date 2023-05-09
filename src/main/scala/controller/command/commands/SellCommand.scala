package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.Capacity
import model.game.gamestate.GameStateManager
import model.game.gamestate.strategies.sell.{SellBuildingStrategy, SellUnitStrategy}
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

  override def execute(): GameStateManager =
    what match
      case building: IBuilding => sellBuilding(building, qty)
      case unit: IUnit => sellUnit(unit, qty)

  private def sellUnit(str: IUnit, quantity: Int): GameStateManager =
    val tupleUnit = removeFromList(gameStateManager.playerValues.listOfUnits, quantity, str)
    val capacitySaved = returnAccumulated(tupleUnit._2, (x: IUnit) => x.capacity, (a: Capacity, b: Capacity) => a.increase(b))
    gameStateManager.sell(
      SellUnitStrategy(tupleUnit._1, calcProfit(tupleUnit._2), capacitySaved),
      msg = sellSuccessMsg(str.name, quantity, calcProfit(tupleUnit._2)))

  private def sellBuilding(str: IBuilding, quantity: Int): GameStateManager =
    val tupleBuilding = removeFromList(gameStateManager.playerValues.listOfBuildings, quantity, str)
    val outputAcc = returnAccumulated(tupleBuilding._2, (x :IBuilding) => x.output, (a: Output, b: Output) => a.increase(b))
    gameStateManager.sell(
      SellBuildingStrategy(tupleBuilding._1, calcProfit(tupleBuilding._2), outputAcc.cap),
      msg = sellSuccessMsg(str.name, quantity, calcProfit(tupleBuilding._2)))

  private def calcProfit(list: List[IUpkeep]): ResourceHolder =
    returnAccumulated(list,
      (x: IUpkeep) => x.cost, (x: ResourceHolder, y:ResourceHolder) => x.increase(y)).divideBy(2)

  private def removeFromList[T<:IGameObject](lst: List[T], qty: Int, what: T): (List[T], List[T]) =
    val listFound: List[T] = lst.filter(_.name == what.name)
    val actualQty = math.min(qty, listFound.length)
    (lst diff listFound.take(actualQty), listFound.take(actualQty))

  private def returnAccumulated[T, R](list: List[T], f: T => R, combiner: (R, R) => R): R =
    if list.length > 1 then list.map(f).reduce(combiner) else list.map(f).head