package model.game.gamestate

import model.game.gamestate.IGameStateManager

/** Interface representing a game state.
 *  The game state provides a method for updating the game state manager.
 */
trait IGameState:
  
  /** Updates the game state manager.
   *
   *  @param gsm: The game state manager.
   *  @return IGameStateManager: The updated game state manager.
   */
  def update(gsm: IGameStateManager): IGameStateManager
