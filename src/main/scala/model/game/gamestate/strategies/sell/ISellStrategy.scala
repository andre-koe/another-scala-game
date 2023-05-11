package model.game.gamestate.strategies.sell

import model.game.gamestate.GameStateManager

trait ISellStrategy:
  
  def sell(gsm: GameStateManager): GameStateManager
