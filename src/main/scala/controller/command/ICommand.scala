package controller.command

import model.game.gamestate.IGameStateManager

trait ICommand:
  def execute(): IGameStateManager

