package model.game.gamestate.gamestates

import model.game.gamestate.{GameStateManager, IGameState}

case class WaitForUserConfirmation() extends IGameState:

  override def update(gsm: GameStateManager): GameStateManager = EndRoundConfirmationState().update(gsm)

  def back(gsm: GameStateManager): GameStateManager =
    gsm.copy(gameState = RunningState(), message = "End round aborted")

  def ask(gsm: GameStateManager): GameStateManager =
    gsm.copy(gameState = WaitForUserConfirmation(), message = "Are you sure? [yes (y) / no (n)]")
