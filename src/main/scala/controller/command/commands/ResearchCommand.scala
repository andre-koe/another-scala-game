package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.{GameValues, IValues}
import model.game.gamestate.IGameStateManager

case class ResearchCommand(string: String, 
                           gameStateManager: IGameStateManager, 
                           gameValues: IValues = GameValues()) extends ICommand, IUndoable:
  override def toString: String = 
    "research <technology name> - Enter list technologies for an overview of all available technologies"
  override def execute(): IGameStateManager =
    string match
      case "help" | "" => gameStateManager.message(this.toString)
      case _ =>
        if gameValues.listOfTechnologies.exists(_.name.toLowerCase == string.toLowerCase())
        then gameStateManager.research(string)
        else gameStateManager.invalid(s"research: '${string}'")