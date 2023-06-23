package model.game.gamestate


import io.circe.*
import io.circe.generic.semiauto.*
import io.circe.syntax.EncoderOps
import model.core.board.*
import model.core.board.boardutils.{ICoordinate, IGameBoardInfoWrapper}
import model.core.board.sector.*
import model.core.board.sector.impl.{IPlayerSector, PlayerSector}
import model.core.fileIO.IFileIOStrategy
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.*
import model.core.gameobjects.purchasable.technology.*
import model.core.gameobjects.purchasable.units.*
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.*
import model.core.utilities.interfaces.IUpkeep
import model.game.gamestate.gamestates.*
import model.game.gamestate.strategies.sell.ISellStrategy
import model.game.playervalues.{IPlayerValues, PlayerValues}
import utils.CirceImplicits.*

import scala.+:
import scala.compiletime.ops.string


case class GameStateManager(round: IRound = Round(),
                            gameMap: IGameBoard = GameBoardBuilder().build,
                            playerValues: IPlayerValues = PlayerValues(),
                            message: String = "",
                            gameState: IGameState = RunningState())
                           (using gameValues: IGameValues) extends IGameStateManager:

  override def getGameValues: IGameValues = this.gameValues

  override def build(sector: IPlayerSector, nB: IResourceHolder, msg: String): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.build(gsm = this, sector = sector, nB = nB, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def research(tech: ITechnology, nB: IResourceHolder, msg: String): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.research(gsm = this, tech = tech, newBalance = nB, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def recruit(sector: IPlayerSector, nB: IResourceHolder, nC: ICapacity, msg: String): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.recruit(gsm = this, sector = sector, nB = nB, nCap = nC, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def sell(sellStrategy: ISellStrategy): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.sell(gsm = this, sellStrategy)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def show(): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.show(this)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def move(what: String, where: ICoordinate): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.move(this, what, where)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def invalid(input: String): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.invalid(this, input)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")
  override def endRoundRequest(): IGameStateManager = WaitForUserConfirmation().ask(this)

  override def accept(): IGameStateManager =
    gameState match
      case x: WaitForUserConfirmation => x.update(this)
      case _ => this.empty()

  override def decline(): IGameStateManager =
    gameState match
      case x: WaitForUserConfirmation => x.back(this)
      case _ => this.empty()

  override def exit(): IGameStateManager =
    gameState match
      case _: RunningState => ExitedState().update(this)
      case _: WaitForUserConfirmation => ExitedState().update(this)
      case _ => this.extCopy(message = "Invalid")

  override def save(fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.save(this, fileIOStrategy, as)
      case _ => this.extCopy(message = "Invalid")

  override def load(as: Option[String], fileIOStrategy: IFileIOStrategy): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.load(this, fileIOStrategy, as)
      case _ => this.extCopy(message = "Invalid")
  
  override def message(what: String): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.showMessage(this, what)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")
    
  override def empty(): IGameStateManager =
    gameState match
      case runningState: RunningState => runningState.update(this)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.extCopy(message = "Invalid")

  override def extCopy(round: IRound = round, gameMap: IGameBoard = gameMap,
                       playerValues: IPlayerValues = playerValues, gameState: IGameState = gameState,
                       message: String = message): IGameStateManager =
    this.copy(round = round, gameMap = gameMap, playerValues = playerValues, gameState = gameState, message = message)


  override def toString: String = message


