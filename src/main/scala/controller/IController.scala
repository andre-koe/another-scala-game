package controller

import controller.command.ICommand
import controller.newInterpreter.TokenizedInput
import model.game.gamestate.IGameStateManagerWrapper
import utils.{Observable, Observer}

/**
 * Represents an interface for a controller in the Model-View-Controller pattern.
 */
trait IController extends Observable:

  /**
   * 
   * @param i either TokenizedInput via TUI or ICommand via GUI
   * @return a boolean indicating if the game was exited
   */
  def processInput(i: TokenizedInput | ICommand): Boolean

  /**
   * @return the wrapped state of the controller
   */
  def getState: IGameStateManagerWrapper

