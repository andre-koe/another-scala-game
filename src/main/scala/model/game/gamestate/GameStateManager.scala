package model.game.gamestate

import GameState.*
import model.game.map.Coordinate
import model.game.{Capacity, GameValues, IValues, PlayerValues, Round}
import model.purchasable.building.{BuildingFactory, EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer, TechnologyFactory}
import model.purchasable.types.EntityType
import model.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, IUnit, UnitFactory}
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

import scala.+:
import scala.compiletime.ops.string

case class GameStateManager(round: Round = Round(),
                            message: String = "",
                            playerValues: PlayerValues = PlayerValues(),
                            gameState: GameState = GameState.INIT) extends IGameStateManager {

  private def nextRound: GameStateManager =
    val newRound = round.next
    //val newFunds = funds.increase(calculateBalance())
    //val newResearch = researchOutput.increase(calculateResearch())

    this.copy(
      round = newRound,
      gameState = GameState.RUNNING,
      message = GameStateStringFormatter(round = newRound, playerValues = playerValues).overview()
    )

  // private def calculateBalance(): Balance = ???
  // private def calculateResearch(): Research = ???
  override def move(what: String, where: Coordinate): IGameStateManager =
    this.copy(gameState = RUNNING, message = "move not implemented yet")
  override def save(as: Option[String]): IGameStateManager =
    this.copy(gameState = RUNNING, message = "save not implemented yet")
  override def load(as: Option[String]): IGameStateManager =
    this.copy(gameState = RUNNING, message = "load not implemented yet")
  override def build(building: IBuilding, newBalance: ResourceHolder, msg: String): IGameStateManager =
    this.copy(
      gameState = RUNNING,
      playerValues = playerValues.copy(
        listOfBuildingsUnderConstruction = playerValues.listOfBuildingsUnderConstruction.+:(building),
        resourceHolder = newBalance),
      message = msg)
  override def research(technology: ITechnology, newBalance: ResourceHolder, msg: String): IGameStateManager =
    this.copy(
      gameState = RUNNING,
      playerValues = playerValues.copy(
        listOfTechnologiesCurrentlyResearched = playerValues.listOfTechnologiesCurrentlyResearched.+:(technology),
        resourceHolder = newBalance),
      message = msg)
  override def recruit(what: Vector[IUnit], newBalance: ResourceHolder, msg: String): IGameStateManager =
    this.copy(
      gameState = RUNNING,
      playerValues = playerValues.copy(
        listOfUnitsUnderConstruction = playerValues.listOfUnitsUnderConstruction.++:(what.toList),
        resourceHolder = newBalance
      ),
      message = msg)
  override def sell(newUnits: Option[List[IUnit]],
                    newBuildings: Option[List[IBuilding]],
                    profit: ResourceHolder,
                    capacity: Capacity,
                    savedUpkeep: ResourceHolder,
                    message: String): IGameStateManager =
    if newUnits.isDefined then
      this.copy(
        gameState = GameState.RUNNING,
        playerValues = playerValues.copy(
            listOfUnits = newUnits.get,
            resourceHolder = playerValues.resourceHolder.increase(profit),
            capacity = playerValues.capacity.increase(capacity)),
        message = message)
    else if newBuildings.isDefined then
      this.copy(
        gameState = GameState.RUNNING,
        playerValues = playerValues.copy(
            listOfBuildings = newBuildings.get,
            resourceHolder = playerValues.resourceHolder.increase(profit),
            capacity = playerValues.capacity.decrease(capacity).get),
        message = message)
    else this.copy(gameState = GameState.RUNNING, message = message)
  override def invalid(input: String): IGameStateManager =
    this.copy(gameState = RUNNING, message = f"$input - invalid\nEnter help to get an " +
      f"overview of all available commands")
  override def show(): IGameStateManager =
    this.copy(gameState = RUNNING,
      message = GameStateStringFormatter(playerValues = this.playerValues)
        .overview(round))
  override def endRoundConfirmation(): IGameStateManager =
    if (gameState == END_ROUND_REQUEST) nextRound
    else this.copy(gameState = RUNNING, message = "")
  override def endRoundRequest(): IGameStateManager =
    this.copy(gameState = END_ROUND_REQUEST, message = "Are you sure? [yes (y) / no (n)]")
  override def exit(): IGameStateManager = this.copy(gameState = EXITED, message = "Goodbye!")
  override def message(what: String): IGameStateManager = this.copy(gameState = RUNNING, message = what)
  override def resetGameState(): IGameStateManager = empty()
  override def empty(): IGameStateManager = this.copy(gameState = RUNNING, message = "")
  override def toString: String = message
}