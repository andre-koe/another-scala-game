package controller.command

import model.game.gamestate.IGameStateManager

trait ICommand:

  def gameStateManager: IGameStateManager
  def execute(): IGameStateManager

