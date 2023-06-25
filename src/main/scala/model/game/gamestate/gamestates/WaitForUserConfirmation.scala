package model.game.gamestate.gamestates

import io.circe.*
import io.circe.generic.auto.*
import model.game.gamestate.{IGameStateManager, IGameState}

case class WaitForUserConfirmation() extends IGameState:

  override def update(gsm: IGameStateManager): IGameStateManager = EndRoundConfirmationState().update(gsm)

  def back(gsm: IGameStateManager): IGameStateManager =
    gsm.extCopy(message = "End round aborted", gameState = RunningState())

  def ask(gsm: IGameStateManager): IGameStateManager =
    gsm.extCopy(message = "Are you sure? [yes (y) / no (n)]", gameState = WaitForUserConfirmation())
