package controller.command

import utils.DefaultValueProvider.given_IGameValues
import model.game.gamestate.IGameStateManager

trait ICommand:

  def gameStateManager: IGameStateManager
  def execute(): IGameStateManager

