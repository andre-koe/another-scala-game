package controller.command

import utils.DefaultValueProvider.given_IGameValues
import model.game.gamestate.IGameStateManager

/**
 * An executable command based on player actions
 */

trait ICommand:

  /**
   * @return the GameStateManager before the execution of the command
   */
  def gameStateManager: IGameStateManager

  /**
   * execute the command
   * @return the GameStateManager after execution
   */
  def execute(): IGameStateManager

