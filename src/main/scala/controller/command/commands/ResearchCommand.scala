package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.GameValues
import model.game.gamestate.GameStateManager
import model.game.purchasable.technology.{ITechnology, TechnologyFactory}
import model.game.resources.ResourceHolder

case class ResearchCommand(technology: ITechnology, gameStateManager: GameStateManager) extends ICommand, IUndoable:

  private def technologyAlreadyResearched(string: String): String =
    s"'${string}' is either being currently researched or has already been researched."

  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}."

  private def successMsg(tech: ITechnology) =
    s"Beginning research of '${tech.name}' " +
      s"for ${tech.cost}, completion in ${tech.roundsToComplete.value} rounds."

  override def execute(): GameStateManager =
    if techAlreadyResearched(technology)
    then gameStateManager.message(technologyAlreadyResearched(technology.name))
    else handleResearch(technology)

  private def handleResearch(tech: ITechnology): GameStateManager =
      checkFunds(tech.cost) match
        case Some(newBalance) => gameStateManager.research(tech, newBalance, successMsg(tech))
        case None => gameStateManager.message(insufficientFundsMsg(tech.cost))

  private def techAlreadyResearched(tech: ITechnology): Boolean =
    gameStateManager.playerValues.listOfTechnologies.exists(_.name == tech.name) ||
      gameStateManager.playerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == tech.name)

  private def checkFunds(cost: ResourceHolder): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost)