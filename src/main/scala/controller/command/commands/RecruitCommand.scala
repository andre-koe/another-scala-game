package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.{Capacity, GameValues}
import model.game.gamestate.GameStateManager
import model.game.purchasable.units.{IUnit, UnitFactory}
import model.game.resources.ResourceHolder

case class RecruitCommand(unit: IUnit, quantity: Int, gameStateManager: GameStateManager) extends ICommand, IUndoable:

  private def successMsg(rUnit: IUnit, quantity: Int) =
    s"Beginning construction of ${quantity} x ${rUnit.name} " +
      s"for ${rUnit.cost.multiplyBy(quantity)}, completion in ${rUnit.roundsToComplete.value} rounds."

  private def insufficientCapsMsg(capacity: Capacity): String =
    s"Insufficient Capacity --- ${gameStateManager.playerValues.capacity.lacking(capacity)}."

  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."
    
  override def execute(): GameStateManager = handleRecruitment(unit, quantity)

  private def handleRecruitment(rUnit: IUnit, quantity: Int): GameStateManager =
    (checkFunds(rUnit.cost, quantity), checkCapacity(rUnit.capacity, quantity)) match
      case (Some(newBalance), Some(newCapacity)) =>
        gameStateManager.recruit(Vector.fill(quantity)(rUnit), newBalance, newCapacity, successMsg(rUnit, quantity))
      case (Some(_), None) =>
        gameStateManager.message(insufficientCapsMsg(rUnit.capacity.multiplyBy(quantity)))
      case _ => gameStateManager.message(insufficientFundsMsg(rUnit.cost.multiplyBy(quantity)))

  private def checkCapacity(cap: Capacity, quantity: Int): Option[Capacity] =
    gameStateManager.playerValues.capacity.decrease(cap.multiplyBy(quantity))

  private def checkFunds(cost: ResourceHolder, quantity: Int): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost.multiplyBy(quantity))