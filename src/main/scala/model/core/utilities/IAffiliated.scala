package model.core.utilities

import model.core.board.sector.sectorutils.Affiliation

/** Interface for all game objects that are affiliated with a specific group.
 *
 *  All affiliated game objects must implement this interface.
 */
trait IAffiliated:

  /** Retrieves the affiliation of the game object.
   *
   *  @return Affiliation: The affiliation of the game object.
   */
  def affiliation: Affiliation