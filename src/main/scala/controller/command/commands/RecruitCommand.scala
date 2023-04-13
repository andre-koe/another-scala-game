package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.GameValues
import model.game.gamestate.IGameStateManager
import model.purchasable.units.{IUnit, UnitFactory}
import model.resources.ResourceHolder

case class RecruitCommand(string: String,
                          gameStateManager: IGameStateManager,
                          gameValues: GameValues = GameValues()) extends ICommand, IUndoable:

  private def unitDoesNotExist(string: String): String =
    s"A unit with name '${string}' does not exist, use " +
    s"'list units' to get an overview of all available units"
  private def successMsg(recruit: IUnit, quantity: Int) =
    s"Beginning construction of ${quantity} x ${recruit.name} " +
      s"for ${recruit.cost.multiplyBy(quantity)}, completion in ${recruit.roundsToComplete.value} rounds."
  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."

  override def execute(): IGameStateManager =
    string match
      case "help" | "" => gameStateManager.message(this.toString)
      case string =>
        formatInput(string) match
          case Some((str, q)) => handleUnit(str, q)
          case _ => gameStateManager.invalid(s"recruit: '$string'")
  override def toString: String =
    "recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
      " - Enter list units for an overview of all available units"
  private def formatInput(str: String): Option[(String, Int)] =
    val input = if str != null then str.split(" ").toList else null
    input match
      case str :: tail =>
        val quantity = tail match
          case q :: Nil if q.toIntOption.isDefined => q.toInt
          case Nil => 1
          case _ => return None
        Some((str, quantity))
      case _ => None
  private def handleUnit(name: String, quantity: Int): IGameStateManager =
    unitExists(name.toLowerCase) match
      case Some(recruit) => handleRecruitment(recruit, quantity)
      case None => gameStateManager.message(unitDoesNotExist(name))
  private def handleRecruitment(recruit: IUnit, quantity: Int): IGameStateManager =
    checkFunds(recruit.cost, quantity) match
      case Some(newBalance) =>
        gameStateManager.recruit(Vector.fill(quantity)(recruit), newBalance, successMsg(recruit, quantity))
      case None => gameStateManager.message(insufficientFundsMsg(recruit.cost.multiplyBy(quantity)))
  private def checkFunds(cost: ResourceHolder, quantity: Int): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost.multiplyBy(quantity))
  private def unitExists(name: String): Option[IUnit] =
    UnitFactory().create(name)