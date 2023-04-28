package model.game.gamestate.gamestates

import model.game.gamestate.{GameStateManager, IGameState}

case class ExitedState() extends IGameState:
  override def update(gsm: GameStateManager): GameStateManager =
    gsm.copy(gameState = ExitedState(), message = goodbyeMsg)

  private def goodbyeMsg: String = "Goodbye!"

