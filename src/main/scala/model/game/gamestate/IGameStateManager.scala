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


trait IGameStateManager extends IXMLSerializable:

  def round: IRound

  def gameMap: IGameBoard

  def message: String

  def currentPlayerIndex: Int
  
  def affiliation: Affiliation

  def currentPlayerValues: IPlayerValues

  def playerValues: Vector[IPlayerValues]

  def gameState: IGameState

  def getGameValues: IGameValues

  def build(sector: IPlayerSector, nB: IResourceHolder, msg: String): IGameStateManager

  def research(tech: ITechnology, nB: IResourceHolder, msg: String): IGameStateManager

  def recruit(sector: IPlayerSector, nB: IResourceHolder, nC: ICapacity, msg: String): IGameStateManager

  def sell(sellStrategy: ISellStrategy): IGameStateManager

  def show(): IGameStateManager

  def move(what: String, where: ICoordinate): IGameStateManager

  def invalid(input: String): IGameStateManager

  def endRoundRequest(): IGameStateManager

  def accept(): IGameStateManager

  def decline(): IGameStateManager

  def exit(): IGameStateManager

  def save(fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager

  def load(fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager

  def message(what: String): IGameStateManager

  def empty(): IGameStateManager

  def extCopy(round: IRound = round,
              gameMap: IGameBoard = gameMap,
              currentPlayerIndex: Int = currentPlayerIndex,
              playerValues: Vector[IPlayerValues] = playerValues,
              gameState: IGameState = gameState,
              message: String = message): IGameStateManager

  override def toXML: scala.xml.Elem =
    <GameStateManager>
      <Round>
        {round.value}
      </Round>
      {gameMap.toXML}
      <CurrentPlayerIndex>
        {currentPlayerIndex}
      </CurrentPlayerIndex>
      <PlayerValuesM>
        {playerValues.map(_.toXML)}
      </PlayerValuesM>
    </GameStateManager>