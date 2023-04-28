package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.gamestate.GameStateManager
import model.game.purchasable.building.{BuildingFactory, IBuilding}
import model.game.resources.ResourceHolder

case class BuildCommand(params: List[String], gameStateManager: GameStateManager) extends ICommand, IUndoable:
  private def buildingDoesNotExist(string: String): String =
    s"A building with name '${string}' does not exist, use " +
      s"'list building' to get an overview of all available buildings."
  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."
  private def successMsg(building: IBuilding) =
    s"Beginning construction of ${building.name} " +
      s"for ${building.cost}, completion in ${building.roundsToComplete.value} rounds."
  override def toString: String =
    "build <building name> - Enter list buildings for an overview of all available buildings."
  override def execute(): GameStateManager =
    params match
      case ("help" | "") :: Nil => gameStateManager.message(this.toString)
      case input :: Nil =>
        buildingExist(input) match
          case Some(building) => handleBuild(building)
          case None => gameStateManager.message(buildingDoesNotExist(input))
  private def buildingExist(name: String): Option[IBuilding] =
    BuildingFactory().create(name.toLowerCase)
  private def handleBuild(building: IBuilding): GameStateManager =
    checkFunds(building.cost) match
      case Some(newBalance) => gameStateManager.build(building, newBalance, successMsg(building))
      case None => gameStateManager.message(insufficientFundsMsg(building.cost))
  private def checkFunds(cost: ResourceHolder): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost)

    
