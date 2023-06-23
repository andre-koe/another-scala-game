package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.units.UnitFactory
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ICapacity, IResourceHolder}
import model.game.gamestate.IGameStateManager

case class RecruitCommand(unit: IUnit, quantity: Int, location: ISector, gameStateManager: IGameStateManager) extends ICommand, IUndoable:

  private def successMsg(rUnit: IUnit, quantity: Int) =
    s"Beginning construction of ${quantity} x ${rUnit.name} " +
      s"for ${rUnit.cost.multiplyBy(quantity)}, completion in ${rUnit.roundsToComplete.value} rounds."

  private def insufficientCapsMsg(capacity: ICapacity): String =
    s"Insufficient Capacity --- ${gameStateManager.playerValues.capacity.lacking(capacity)}."

  private def insufficientFundsMsg(cost: IResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."

  private def invalidSector(sector: ISector): String =
    s"Can't begin construction in ${sector.toString} - is not a player sector"
    
  override def execute(): IGameStateManager = validateSector(location)

  private def validateSector(location: ISector): IGameStateManager =
    location match
      case x : IPlayerSector => handleRecruitment(unit, quantity, x)
      case _ => gameStateManager.message(invalidSector(location))

  private def handleRecruitment(rUnit: IUnit, qty: Int, sector: IPlayerSector): IGameStateManager =
    (checkFunds(rUnit.cost, qty), checkCapacity(rUnit.capacity, qty)) match
      case (Some(newBalance), Some(newCapacity)) =>
        gameStateManager.recruit(sector.constructUnit(rUnit, qty), newBalance, newCapacity, successMsg(rUnit, qty))
      case (Some(_), None) => gameStateManager.message(insufficientCapsMsg(rUnit.capacity.multiplyBy(qty)))
      case _ => gameStateManager.message(insufficientFundsMsg(rUnit.cost.multiplyBy(qty)))

  private def checkCapacity(cap: ICapacity, quantity: Int): Option[ICapacity] =
    gameStateManager.playerValues.capacity.decrease(cap.multiplyBy(quantity))

  private def checkFunds(cost: IResourceHolder, quantity: Int): Option[IResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost.multiplyBy(quantity))