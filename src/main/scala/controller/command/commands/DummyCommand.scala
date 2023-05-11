package controller.command.commands

import controller.command.{ICommand, IUndoable}
import model.game.gamestate.GameStateManager

case class DummyCommand(gameStateManager: GameStateManager) extends ICommand, IUndoable:
  override def execute(): GameStateManager = gameStateManager
