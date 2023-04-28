package controller.command

import model.game.gamestate.GameStateManager

trait ICommand:
  def execute(): GameStateManager

