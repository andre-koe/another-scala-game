package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.GameValues
import model.game.gamestate.GameStateManager
import model.game.purchasable.technology.{ITechnology, TechnologyFactory}
import model.game.resources.ResourceHolder

case class ResearchCommand(string: List[String], gameStateManager: GameStateManager) extends ICommand, IUndoable:
  
  private def technologyDoesNotExist(string: String): String = 
    s"A technology with name '${string}' does not exist, use " +
      s"'list tech' to get an overview of all available technologies"

  private def technologyAlreadyResearched(string: String): String =
    s"'${string}' is either being currently researched or has already been researched"

  private def insufficientFundsMsg(cost: ResourceHolder): String =
    s"Insufficient Funds --- ${gameStateManager.playerValues.resourceHolder.lacking(cost)}"

  private def successMsg(tech: ITechnology) =
    s"Beginning research of ${tech.name} " +
      s"for ${tech.cost}, completion in ${tech.roundsToComplete.value} rounds"

  override def toString: String =
    "research <technology name> - Enter list technologies for an overview of all available technologies"

  override def execute(): GameStateManager =
    string match
      case ("help" | "") :: Nil => gameStateManager.message(this.toString)
      case name :: Nil =>
        techExists(name) match
          case Some(tech) => handleResearch(tech)
          case None => gameStateManager.message(technologyDoesNotExist(name))

  private def handleResearch(tech: ITechnology): GameStateManager =
    if techAlreadyResearched(tech) 
    then gameStateManager.message(technologyAlreadyResearched(tech.name))
    else 
      checkFunds(tech.cost) match
        case Some(newBalance) => gameStateManager.research(tech, newBalance, successMsg(tech))
        case None => gameStateManager.message(insufficientFundsMsg(tech.cost))

  private def techExists(name: String): Option[ITechnology] = TechnologyFactory().create(name)

  private def techAlreadyResearched(tech: ITechnology): Boolean =
    gameStateManager.playerValues.listOfTechnologies.exists(_.name == tech.name) ||
      gameStateManager.playerValues.listOfTechnologiesCurrentlyResearched.exists(_.name == tech.name)

  private def checkFunds(cost: ResourceHolder): Option[ResourceHolder] =
    gameStateManager.playerValues.resourceHolder.decrease(cost)