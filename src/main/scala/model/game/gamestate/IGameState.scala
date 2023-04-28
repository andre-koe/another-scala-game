package model.game.gamestate

import model.game.gamestate.GameStateManager

trait IGameState:
  def update(gsm: GameStateManager): GameStateManager

