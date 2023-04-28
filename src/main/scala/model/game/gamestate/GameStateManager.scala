package model.game.gamestate

import model.game.gamestate.gamestates.{EndRoundConfirmationState, WaitForEndRoundConfirmation, ExitedState, RunningState}
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
                            gameState: IGameState = RunningState()):
  
  val gameValues: GameValues = GameValues()

  def build(building: IBuilding, newBalance: ResourceHolder, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState =>
        runningState.build(gsm = this, building = building, newBalance = newBalance, msg = msg)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def research(technology: ITechnology, newBalance: ResourceHolder, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState =>
        runningState.research(gsm = this, technology = technology, newBalance = newBalance, msg = msg)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def recruit(what: Vector[IUnit], nB: ResourceHolder, nC: Capacity, msg: String): GameStateManager =
    gameState match
      case runningState: RunningState =>
        runningState.recruit(gsm = this, what = what, nBalance = nB, nCap = nC, msg = msg)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def sell(newUnits: List[IUnit],
           newBuildings: List[IBuilding],
           profit: ResourceHolder,
           capacity: Capacity,
           message: String): GameStateManager =
    gameState match
      case runningState: RunningState =>
        runningState.sell(gsm = this, nU = newUnits, nB = newBuildings, profit = profit, cap = capacity, msg = message)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def show(): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.show(this)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def move(what: String, where: Coordinate): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.move(this, what, where)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")

  def invalid(input: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.invalid(this, input)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
      case _ => this.copy(message = "Invalid")
  def endRoundRequest(): GameStateManager = WaitForEndRoundConfirmation().ask(this)

  def accept(): GameStateManager =
    gameState match
      case userChoiceRequestedState: WaitForEndRoundConfirmation => userChoiceRequestedState.update(this)
      case _ => this.empty()

  def decline(): GameStateManager =
    gameState match
      case userChoiceRequestedState: WaitForEndRoundConfirmation => userChoiceRequestedState.back(this)
      case _ => this.empty()
      
  def resetGameState(): GameStateManager = this.copy(gameState = RunningState(), message = "")

  def exit(): GameStateManager = ExitedState().update(this)

  def save(as: Option[String]): GameStateManager =
    this.copy(message = "save not implemented yet")

  def load(as: Option[String]): GameStateManager =
    this.copy(message = "load not implemented yet")
  
  def message(what: String): GameStateManager =
    gameState match
      case runningState: RunningState => runningState.showMessage(this, what)
      case endRoundRequestState: WaitForEndRoundConfirmation => endRoundRequestState.ask(this)
    
  def empty(): GameStateManager = this.copy(message = "")

  override def toString: String = message
