package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.gamestate.IGameStateManager

case class SellCommand(string: String, gameStateManager: IGameStateManager) extends ICommand, IUndoable:
  override def execute(): IGameStateManager = gameStateManager.sell(string, 1)
