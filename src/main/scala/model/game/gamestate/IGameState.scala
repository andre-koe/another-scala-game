package model.game.gamestate

import model.game.gamestate.IGameStateManager

trait IGameState:
  def update(gsm: IGameStateManager): IGameStateManager