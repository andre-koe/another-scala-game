package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.{GameValues, IValues}
import model.game.gamestate.IGameStateManager

case class BuildCommand(string: String,
                        gameStateManager: IGameStateManager,
                        gameValues: IValues = GameValues()) extends ICommand, IUndoable:
  override def execute(): IGameStateManager =
    string match
      case "help" | "" => gameStateManager.message(this.toString)
      case _ => 
        if gameValues.listOfBuildings.exists(_.name.toLowerCase == string.toLowerCase())
        then gameStateManager.build(string) 
        else gameStateManager.invalid("build " + string)
  override def toString: String = 
    "build <building name> - Enter list buildings for an overview of all available buildings"
    
