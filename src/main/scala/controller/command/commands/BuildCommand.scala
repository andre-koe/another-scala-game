package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.utilities.IResourceHolder
import model.game.gamestate.IGameStateManager

case class BuildCommand(building: IBuilding, location: ISector, gameStateManager: IGameStateManager) extends ICommand, IUndoable:
  
  private def insufficientFundsMsg(cost: IResourceHolder): String =
    s"insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."

  private def insufficientBuildSlots(sector: ISector): String =
    s"Sector ${sector.toString} does not provide enough build slots for construction"

  private def invalidSector(sector: ISector): String =
    s"Can't begin construction in ${sector.toString} - is not a player sector"

  private def validateSector(building: IBuilding, sector: ISector): IGameStateManager =
    sector match
      case x: IPlayerSector if x.buildSlots.value > x.buildingsInSector.length => handleBuild(x, building)
      case _: IPlayerSector => gameStateManager.message(insufficientBuildSlots(sector))
      case _ => gameStateManager.message(invalidSector(sector))

  private def successMsg(building: IBuilding) =
    s"Beginning construction of ${building.name} " +
      s"for ${building.cost}, completion in ${building.roundsToComplete.value} rounds."

  override def execute(): IGameStateManager = validateSector(building, location)

  private def handleBuild(playerSector: IPlayerSector, b: IBuilding): IGameStateManager =
    checkFunds(b.cost) match
      case Some(newBalance) => gameStateManager.build(playerSector.constructBuilding(b), newBalance, successMsg(b))
      case None => gameStateManager.message(insufficientFundsMsg(b.cost))

  private def checkFunds(cost: IResourceHolder): Option[IResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost)
