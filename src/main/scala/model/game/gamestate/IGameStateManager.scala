package model.game.gamestate

import model.core.board.IGameBoard
import model.core.board.boardutils.ICoordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.board.sector.sectorutils.Affiliation
import model.core.fileIO.IFileIOStrategy
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.utilities.*
import model.game.gamestate.strategies.sell.ISellStrategy
import model.game.playervalues.IPlayerValues
import utils.{CirceImplicits, IXMLSerializable}

import scala.xml.Elem


/** Interface representing a game state manager (Model Controller).
 *  The game state manager handles various operations and actions in the game.
 */
trait IGameStateManager extends IXMLSerializable:

  /** The current round of the game. */
  def round: IRound

  /** The game map of the game. */
  def gameMap: IGameBoard

  /** The message displayed in the game. */
  def message: String

  /** The index of the current player. */
  def currentPlayerIndex: Int

  /** The affiliation of the game. */
  def affiliation: Affiliation

  /** The values specific to the current player. */
  def currentPlayerValues: IPlayerValues

  /** The values for all players in the game. */
  def playerValues: Vector[IPlayerValues]

  /** The current game state. */
  def gameState: IGameState

  /** Retrieves the game values. */
  def getGameValues: IGameValues

  /** Builds a structure in the specified sector.
   *
   *  @param sector: The player sector.
   *  @param nB: The resource holder for the building cost.
   *  @param msg: The message associated with the action.
   *  @return IGameStateManager: The updated game state manager.
   */
  def build(sector: IPlayerSector, nB: IResourceHolder, msg: String): IGameStateManager

  /** Researches a technology.
   *
   *  @param tech: The technology to research.
   *  @param nB: The resource holder for the research cost.
   *  @param msg: The message associated with the action.
   *  @return IGameStateManager: The updated game state manager.
   */
  def research(tech: ITechnology, nB: IResourceHolder, msg: String): IGameStateManager

  /** Recruits units in the specified sector.
   *
   *  @param sector: The player sector.
   *  @param nB: The resource holder for the unit cost.
   *  @param nC: The capacity for the units.
   *  @param msg: The message associated with the action.
   *  @return IGameStateManager: The updated game state manager.
   */
  def recruit(sector: IPlayerSector, nB: IResourceHolder, nC: ICapacity, msg: String): IGameStateManager

  /** Executes a sell strategy.
   *
   *  @param sellStrategy: The sell strategy to execute.
   *  @return IGameStateManager: The updated game state manager.
   */
  def sell(sellStrategy: ISellStrategy): IGameStateManager

  /** Shows the current state of the game.
   *
   *  @return IGameStateManager: The updated game state manager.
   */
  def show(): IGameStateManager

  /** Moves a game element to the specified coordinate.
   *
   *  @param what: The element to move.
   *  @param where: The target coordinate.
   *  @return IGameStateManager: The updated game state manager.
   */
  def move(what: String, where: ICoordinate): IGameStateManager

  /** Handles an invalid input.
   *
   *  @param input: The invalid input.
   *  @return IGameStateManager: The updated game state manager.
   */
  def invalid(input: String): IGameStateManager

  /** Requests to end the current round.
   *
   *  @return IGameStateManager: The updated game state manager.
   */
  def endRoundRequest(): IGameStateManager

  /** Accepts an action or request.
   *
   *  @return IGameStateManager: The updated game state manager.
   */
  def accept(): IGameStateManager

  /** Declines an action or request.
   *
   *  @return IGameStateManager: The updated game state manager.
   */
  def decline(): IGameStateManager

  /** Exits the game.
   *
   *  @return IGameStateManager: The updated game state manager.
   */
  def exit(): IGameStateManager

  /** Saves the game state to a file.
   *
   *  @param fileIOStrategy: The file I/O strategy.
   *  @param as: The optional file name.
   *  @return IGameStateManager: The updated game state manager.
   */
  def save(fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager

  /** Loads the game state from a file.
   *
   *  @param fileIOStrategy: The file I/O strategy.
   *  @param as: The optional file name.
   *  @return IGameStateManager: The updated game state manager.
   */
  def load(fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager

  /** Sets the message to display in the game.
   *
   *  @param what: The message to set.
   *  @return IGameStateManager: The updated game state manager.
   */
  def message(what: String): IGameStateManager

  /** Creates an empty game state manager.
   *
   *  @return IGameStateManager: The empty game state manager.
   */
  def empty(): IGameStateManager

  /** Creates a copy of the game state manager with updated values.
   *
   *  @param round: The new round value.
   *  @param gameMap: The new game map.
   *  @param currentPlayerIndex: The new current player index.
   *  @param playerValues: The new player values.
   *  @param gameState: The new game state.
   *  @param message: The new message.
   *  @return IGameStateManager: The updated game state manager.
   */
  def extCopy(round: IRound = round,
              gameMap: IGameBoard = gameMap,
              currentPlayerIndex: Int = currentPlayerIndex,
              playerValues: Vector[IPlayerValues] = playerValues,
              gameState: IGameState = gameState,
              message: String = message): IGameStateManager

  /** Converts the game state manager to XML format.
   *
   *  @return Elem: The XML representation of the game state manager.
   */
  override def toXML: scala.xml.Elem =
    <GameStateManager>
      <Round>{round.value}</Round>
      {gameMap.toXML}
      <CurrentPlayerIndex>{currentPlayerIndex}</CurrentPlayerIndex>
      <PlayerValuesM>{playerValues.map(_.toXML)}</PlayerValuesM>
    </GameStateManager>
