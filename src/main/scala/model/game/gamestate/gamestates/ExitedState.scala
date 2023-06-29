package model.game.gamestate.gamestates

import io.circe.*
import io.circe.generic.auto.*
import model.game.gamestate.{IGameState, IGameStateManager}

case class ExitedState() extends IGameState:
  
  override def update(gsm: IGameStateManager): IGameStateManager = 
    gsm.extCopy(message = goodbyeMsg, gameState = ExitedState())

  private def goodbyeMsg: String = "Goodbye!"

