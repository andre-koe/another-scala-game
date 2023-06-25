package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.core.gameobjects.purchasable.technology.{ITechnology, TechnologyFactory}
import model.core.utilities.{GameValues, IResourceHolder, ResourceHolder}
import model.game.gamestate.IGameStateManager

case class ResearchCommand(technology: ITechnology, gameStateManager: IGameStateManager) extends ICommand, IUndoable:

  private def technologyAlreadyResearched(string: String): String =
    s"'${string}' is either being currently researched or has already been researched."

  private def insufficientFundsMsg(cost: IResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.currentPlayerValues.resourceHolder.lacking(cost)}."

  private def successMsg(tech: ITechnology) =
    s"Beginning research of '${tech.name}' " +
      s"for ${tech.cost}, completion in ${tech.roundsToComplete.value} rounds."

  override def execute(): IGameStateManager =
    if techAlreadyResearched(technology)
    then gameStateManager.message(technologyAlreadyResearched(technology.name))
    else handleResearch(technology)

  private def handleResearch(tech: ITechnology): IGameStateManager =
      checkFunds(tech.cost) match
        case Some(newBalance) => gameStateManager.research(tech, newBalance, successMsg(tech))
        case None => gameStateManager.message(insufficientFundsMsg(tech.cost))

  private def techAlreadyResearched(tech: ITechnology): Boolean =
    gameStateManager.currentPlayerValues.listOfTechnologies.exists(_.name == tech.name) ||
      gameStateManager.currentPlayerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == tech.name)

  private def checkFunds(cost: IResourceHolder): Option[IResourceHolder] =
    gameStateManager.currentPlayerValues.resourceHolder.decrease(cost)