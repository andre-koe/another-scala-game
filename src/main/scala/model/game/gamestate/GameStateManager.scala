package model.game.gamestate

import model.game.gamestate.gamestates.{EndRoundConfirmationState, ExitedState, RunningState, WaitForUserConfirmation}
import model.game.gamestate.strategies.sell.ISellStrategy
import model.game.map.{Coordinate, GameMap}
import model.game.purchasable.building.{BuildingFactory, EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.game.purchasable.{IGameObject, IUpkeep}
import model.game.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer, TechnologyFactory}
import model.game.purchasable.types.EntityType
import model.game.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, IUnit, UnitFactory}
import model.game.purchasable.utils.Output
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}
import model.game.{Capacity, GameValues, PlayerValues, Round}

import scala.+:
import scala.compiletime.ops.string

case class GameStateManager(round: Round = Round(),
                            gameMap: GameMap = GameMap(),
                            message: String = "",
                            playerValues: PlayerValues = PlayerValues(),
                            gameState: IGameState = RunningState(),
                            gameValues: GameValues = GameValues()):

  def build(building: IBuilding, nB: ResourceHolder, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.build(gsm = this, building = building, nB = nB, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def research(tech: ITechnology, nB: ResourceHolder, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.research(gsm = this, tech = tech, newBalance = nB, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def recruit(what: Vector[IUnit], nB: ResourceHolder, nC: Capacity, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.recruit(gsm = this, what = what, nB = nB, nCap = nC, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def sell(sellStrategy: ISellStrategy, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.sell(gsm = this, sellStrategy, msg = msg)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def show(): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.show(this)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def move(what: String, where: Coordinate): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.move(this, what, where)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def invalid(input: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.invalid(this, input)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")
  def endRoundRequest(): GameStateManager = WaitForUserConfirmation().ask(this)

  def accept(): GameStateManager =
    gameState match
      case x: WaitForUserConfirmation => x.update(this)
      case _ => this.empty()

  def decline(): GameStateManager =
    gameState match
      case x: WaitForUserConfirmation => x.back(this)
      case _ => this.empty()

  def exit(): GameStateManager =
    gameState match
      case _: RunningState => ExitedState().update(this)
      case _: WaitForUserConfirmation => ExitedState().update(this)
      case _ => this.copy(message = "Invalid")

  def save(as: Option[String]): GameStateManager =
    gameState match
      case _: RunningState => this.copy(message = "save not implemented yet")
      case _ => this.copy(message = "Invalid")

  def load(as: Option[String]): GameStateManager =
    gameState match
      case _: RunningState => this.copy(message = "load not implemented yet")
      case _ => this.copy(message = "Invalid")
  
  def message(what: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.showMessage(this, what)
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")
    
  def empty(): GameStateManager =
    gameState match
      case runningState: RunningState => this.copy(message = "")
      case endRoundRequestState: WaitForUserConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  override def toString: String = message
