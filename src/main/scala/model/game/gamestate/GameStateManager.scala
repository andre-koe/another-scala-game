package model.game.gamestate

import GameState.*
import model.game.map.{Coordinate, GameMap}
import model.game.{Capacity, GameValues, IValues, PlayerValues, Round}
import model.purchasable.{IGameObject, IUpkeep}
import model.purchasable.building.{BuildingFactory, EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer, TechnologyFactory}
import model.purchasable.types.EntityType
import model.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, IUnit, UnitFactory}
import model.purchasable.utils.Output
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

import scala.+:
import scala.compiletime.ops.string

case class GameStateManager(round: Round = Round(),
                            gameMap: GameMap = GameMap(),
                            message: String = "",
                            playerValues: PlayerValues = PlayerValues(),
                            gameState: GameState = GameState.INIT) extends IGameStateManager {

  private def nextRound: GameStateManager =
    val newRound = round.next

    val buildings = handleList(playerValues.listOfBuildingsUnderConstruction)
    val units = handleList(playerValues.listOfUnitsUnderConstruction)
    val tech = handleList(playerValues.listOfTechnologiesCurrentlyResearched)

    val newUpkeep = calcUpkeep(
      (buildings._2 ++ units._2 ++ playerValues.listOfUnits ++ playerValues.listOfBuildings)
        .asInstanceOf[List[IUpkeep]])

    val buildingsCompleted = buildings._2.asInstanceOf[List[IBuilding]] ++ playerValues.listOfBuildings
    val output: Output = calcOutput(buildingsCompleted)
    val income = output.resourceHolder

    // TODO: Add handler if current balance negative reduce Unit strength
    val newBalance = playerValues.resourceHolder.increase(income).decrease(newUpkeep).get
    val buildingsUnderConstruction = buildings._1.asInstanceOf[List[IBuilding]]
    val unitsCompleted = units._2.asInstanceOf[List[IUnit]] ++ playerValues.listOfUnits
    val unitsUnderConstruction = units._1.asInstanceOf[List[IUnit]]
    val techCompleted = tech._2.asInstanceOf[List[ITechnology]] ++ playerValues.listOfTechnologies
    val techCurrentlyResearched = tech._1.asInstanceOf[List[ITechnology]]

    val newPlayerValues = this.playerValues.copy(
      resourceHolder = newBalance,
      listOfUnits = unitsCompleted,
      listOfBuildings = buildingsCompleted,
      listOfTechnologies = techCompleted,
      listOfTechnologiesCurrentlyResearched = techCurrentlyResearched,
      listOfUnitsUnderConstruction = unitsUnderConstruction,
      listOfBuildingsUnderConstruction = buildingsUnderConstruction,
      income = income,
      upkeep = newUpkeep
    )

    
    this.copy(
      round = newRound,
      gameState = GameState.RUNNING,
      playerValues = newPlayerValues,
      message = GameStateStringFormatter(round = newRound, playerValues = newPlayerValues).overview())


  private def calcOutput(list: List[IBuilding]): Output =
    list match
      case Nil => Output()
      case value :: Nil => value.output
      case _ => list.map(_.output).reduce((x,y) => x.increaseOutput(y))
  private def calcUpkeep(list: List[IUpkeep]): ResourceHolder =
    list match
      case Nil => ResourceHolder()
      case value :: Nil => value.upkeep
      case _ => list.map(_.upkeep).reduce((x, y) => x.increase(y))
  private def handleList(list: List[IGameObject]): (List[IGameObject], List[IGameObject]) =
    list.map(_.decreaseRoundsToComplete).partition(_.roundsToComplete.value != 0)
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
  override def recruit(what: Vector[IUnit], nBalance: ResourceHolder, nCap: Capacity, msg: String): IGameStateManager =
    this.copy(
      gameState = RUNNING,
      playerValues = playerValues.copy(
        listOfUnitsUnderConstruction = playerValues.listOfUnitsUnderConstruction.++:(what.toList),
        resourceHolder = nBalance,
        capacity = nCap
      ),
      message = msg)
  override def sell(nU: Option[List[IUnit]],
                    nB: Option[List[IBuilding]],
                    profit: ResourceHolder,
                    cap: Capacity,
                    msg: String): IGameStateManager =

    if nU.isDefined then
      sellUnits(nU.get, playerValues.resourceHolder.increase(profit), playerValues.capacity.increase(cap), msg)
    else if nB.isDefined then
      sellBuilding(nB.get, playerValues.resourceHolder.increase(profit), playerValues.capacity.decrease(cap).get, msg)
    else this.copy(gameState = GameState.RUNNING, message = msg)
  private def sellUnits(units: List[IUnit], profit: ResourceHolder, cap: Capacity, msg: String): IGameStateManager =
    this.copy(
      gameState = GameState.RUNNING,
      playerValues = playerValues.copy(
        listOfUnits = units,
        resourceHolder = profit,
        capacity = cap),
      message = msg)
  private def sellBuilding(building: List[IBuilding], profit: ResourceHolder, cap: Capacity, msg: String): IGameStateManager =
    this.copy(
      gameState = GameState.RUNNING,
      playerValues = playerValues.copy(
        listOfBuildings = building,
        resourceHolder = profit,
        capacity = cap),
      message = msg)
  override def invalid(input: String): IGameStateManager =
    this.copy(gameState = RUNNING, message = f"$input - invalid\nEnter help to get an " +
      f"overview of all available commands")
  override def show(): IGameStateManager =
    this.copy(gameState = RUNNING,
      message = GameStateStringFormatter(playerValues = this.playerValues, gameStateManager = this)
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