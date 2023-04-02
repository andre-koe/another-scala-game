package controller.command.commands

import controller.command.ICommand
import model.game.gamestate.IGameStateManager

case class HelpCommand(string: String, gameStateManager: IGameStateManager) extends ICommand:
  override def execute(): IGameStateManager =
    string match
      case "" => gameStateManager.help(None)
      case _ => gameStateManager.help(Option(string))
