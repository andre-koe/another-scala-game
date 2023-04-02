package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class ShowCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:

  override def toString: String = "show overview - display the current player stats buildings, tech, units, income, etc."
  override def execute(): IGameStateManager =
    string match
      case "help" | "" => gameStateManager.message(this.toString)
      case "overview" => gameStateManager.show()
      case _ => gameStateManager.invalid(s"show: '${string}'")
