package controller.command.memento

import model.core.utilities.GameValues
import model.game.gamestate.{GameStateManager, IGameStateManager}

import scala.util.Try

case class GameStateManagerMemento(state: String = ""):

  def storeState(gsm: IGameStateManager): GameStateManagerMemento =
    this.copy(state = state) //)

  def getState: Try[IGameStateManager] = ???