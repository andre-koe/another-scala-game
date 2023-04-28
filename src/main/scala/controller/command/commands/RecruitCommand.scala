package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.{Capacity, GameValues}
import model.game.gamestate.GameStateManager
import model.game.purchasable.units.{IUnit, UnitFactory}
import model.game.resources.ResourceHolder

case class RecruitCommand(string: List[String], gameStateManager: GameStateManager) extends ICommand, IUndoable:

  private def unitDoesNotExist(string: String): String =
    s"A unit with name '${string}' does not exist, use " +
    s"'list units' to get an overview of all available units"

  private def successMsg(recruit: IUnit, quantity: Int) =
    s"Beginning construction of ${quantity} x ${recruit.name} " +
      s"for ${recruit.cost.multiplyBy(quantity)}, completion in ${recruit.roundsToComplete.value} rounds."

  private def insufficientCapsMsg(capacity: Capacity): String =
    s"Insufficient Capacity --- ${gameStateManager.playerValues.capacity.lacking(capacity)}."

  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."
    
  override def execute(): GameStateManager =
    string match
      case ("help" | "") :: Nil => gameStateManager.message(this.toString)
      case string =>
        formatInput(string) match
          case Some((str, q)) => handleUnit(str, q)
          case _ => gameStateManager.invalid(s"recruit: '$string'")

  override def toString: String =
    "recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
      " - Enter list units for an overview of all available units"

  private def formatInput(input: List[String]): Option[(String, Int)] =
    input match
      case str :: tail =>
        val quantity = tail match
          case q :: Nil if q.toIntOption.isDefined => q.toInt
          case Nil => 1
          case _ => return None
        Some((str, quantity))
      case _ => None

  private def handleUnit(name: String, quantity: Int): GameStateManager =
    unitExists(name.toLowerCase) match
      case Some(recruit) => handleRecruitment(recruit, quantity)
      case None => gameStateManager.message(unitDoesNotExist(name))

  private def handleRecruitment(recruit: IUnit, quantity: Int): GameStateManager =
    (checkFunds(recruit.cost, quantity), checkCapacity(recruit.capacity, quantity)) match
      case (Some(newBalance), Some(newCapacity)) =>
        gameStateManager.recruit(Vector.fill(quantity)(recruit), newBalance, newCapacity, successMsg(recruit, quantity))
      case (Some(_), None) =>
        gameStateManager.message(insufficientCapsMsg(recruit.capacity.multiplyBy(quantity)))
      case _ => gameStateManager.message(insufficientFundsMsg(recruit.cost.multiplyBy(quantity)))

  private def checkCapacity(cap: Capacity, quantity: Int): Option[Capacity] =
    gameStateManager.playerValues.capacity.decrease(cap.multiplyBy(quantity))

  private def checkFunds(cost: ResourceHolder, quantity: Int): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost.multiplyBy(quantity))

  private def unitExists(name: String): Option[IUnit] = UnitFactory().create(name)