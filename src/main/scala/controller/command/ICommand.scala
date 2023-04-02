package controller.command

import model.game.gamestate.IGameStateManager
import controller.playeractions.ActionType
import controller.playeractions.ActionType._

trait ICommand:
  def execute(): IGameStateManager

