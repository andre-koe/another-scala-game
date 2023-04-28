package model.game.gamestate.gamestates

import model.game.gamestate.{GameStateManager, IGameState}

case class WaitForEndRoundConfirmation() extends IGameState:

  override def update(gsm: GameStateManager): GameStateManager =
    gsm.copy(gameState = EndRoundConfirmationState())

  def back(gsm: GameStateManager): GameStateManager =
    gsm.copy(gameState = RunningState(), message = "End round aborted")

  def ask(gsm: GameStateManager): GameStateManager =
    gsm.copy(gameState = WaitForEndRoundConfirmation(), message = "Are you sure? [yes (y) / no (n)]")
