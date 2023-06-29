package model.game.gamestate

import model.core.board.boardutils.IGameBoardInfoWrapper
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.gameobjects.resources.IResource
import model.core.mechanics.fleets.components.units.IUnit

/** Interface representing a wrapper for the game state manager.
 *  Provides convenient methods to retrieve various information from the game state manager.
 */
trait IGameStateManagerWrapper:

  /** Retrieves the current round.
   *
   *  @return Int: The current round.
   */
  def getCurrentRound: Int

  /** Retrieves the capacity value.
   *
   *  @return Int: The capacity value.
   */
  def getCapacity: Int

  /** Retrieves the technologies owned by the player.
   *
   *  @return Vector[ITechnology]: The technologies owned by the player.
   */
  def getPlayerTech: Vector[ITechnology]

  /** Retrieves the technologies currently being researched by the player.
   *
   *  @return Vector[ITechnology]: The technologies currently being researched by the player.
   */
  def getPlayerTechCurrentlyResearch: Vector[ITechnology]

  /** Retrieves the resources owned by the player.
   *
   *  @return Vector[IResource[_]]: The resources owned by the player.
   */
  def getPlayerResources: Vector[IResource[_]]

  /** Retrieves the income of the player.
   *
   *  @return Vector[IResource[_]]: The income of the player.
   */
  def getPlayerIncome: Vector[IResource[_]]

  /** Retrieves the upkeep of the player.
   *
   *  @return Vector[IResource[_]]: The upkeep of the player.
   */
  def getPlayerUpkeep: Vector[IResource[_]]

  /** Retrieves the net income of the player.
   *
   *  @return Vector[IResource[_]]: The net income of the player.
   */
  def getPlayerNetIncome: Vector[IResource[_]]

  /** Retrieves information about the game map.
   *
   *  @return IGameBoardInfoWrapper: The information about the game map.
   */
  def getGameMapInfo: IGameBoardInfoWrapper

  /** Retrieves the current game state.
   *
   *  @return IGameState: The current game state.
   */
  def getState: IGameState

  /** Retrieves the technologies that can be researched by the player.
   *
   *  @return Vector[ITechnology]: The technologies that can be researched by the player.
   */
  def getResearchableTech: Vector[ITechnology]

  /** Retrieves the buildings that can be constructed by the player.
   *
   *  @return Vector[IBuilding]: The buildings that can be constructed by the player.
   */
  def getConstructableBuildings: Vector[IBuilding]

  /** Retrieves the units that can be recruited by the player.
   *
   *  @return Vector[IUnit]: The units that can be recruited by the player.
   */
  def getRecruitableUnits: Vector[IUnit]

  /** Retrieves the size of the game map along the X-axis.
   *
   *  @return Int: The size of the game map along the X-axis.
   */
  def getGameMapSizeX: Int

  /** Retrieves the size of the game map along the Y-axis.
   *
   *  @return Int: The size of the game map along the Y-axis.
   */
  def getGameMapSizeY: Int

  /** Retrieves the underlying game state manager.
   *
   *  @return IGameStateManager: The underlying game state manager.
   */
  def getGSM: IGameStateManager



