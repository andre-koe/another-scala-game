package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.gamestate.GameStateManager
import model.game.purchasable.building.{BuildingFactory, IBuilding}
import model.game.resources.ResourceHolder

case class BuildCommand(building: IBuilding, gameStateManager: GameStateManager) extends ICommand, IUndoable:
  
  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."

  private def successMsg(building: IBuilding) =
    s"Beginning construction of ${building.name} " +
      s"for ${building.cost}, completion in ${building.roundsToComplete.value} rounds."

  override def execute(): GameStateManager = handleBuild(building)

  private def handleBuild(building: IBuilding): GameStateManager =
    checkFunds(building.cost) match
      case Some(newBalance) => gameStateManager.build(building, newBalance, successMsg(building))
      case None => gameStateManager.message(insufficientFundsMsg(building.cost))

  private def checkFunds(cost: ResourceHolder): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost)

    
