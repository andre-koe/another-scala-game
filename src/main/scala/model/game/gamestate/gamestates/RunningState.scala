package model.game.gamestate.gamestates

import model.game.Capacity
import model.game.gamestate.{GameStateManager, GameStateStringFormatter, IGameState}
import model.game.map.Coordinate
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder

case class RunningState() extends IGameState:

  def update(gsm: GameStateManager): GameStateManager = gsm

  def build(gsm: GameStateManager, building: IBuilding, newBalance: ResourceHolder, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfBuildingsUnderConstruction = gsm.playerValues.listOfBuildingsUnderConstruction.+:(building),
        resourceHolder = newBalance),
      message = msg)

  def research(gsm: GameStateManager, technology: ITechnology, newBalance: ResourceHolder, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfTechnologiesCurrentlyResearched = gsm.playerValues.listOfTechnologiesCurrentlyResearched.+:(technology),
        resourceHolder = newBalance),
      message = msg)

  def recruit(gsm: GameStateManager, what: Vector[IUnit], nBalance: ResourceHolder, nCap: Capacity, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfUnitsUnderConstruction = gsm.playerValues.listOfUnitsUnderConstruction.++:(what.toList),
        resourceHolder = nBalance,
        capacity = nCap
      ),
      message = msg)

  def sell(gsm: GameStateManager,
           nU: List[IUnit],
           nB: List[IBuilding],
           profit: ResourceHolder,
           cap: Capacity,
           msg: String): GameStateManager =
    if nU.nonEmpty then
      sellUnits(gsm, nU, gsm.playerValues.resourceHolder.increase(profit), gsm.playerValues.capacity.increase(cap), msg)
    else if nU.nonEmpty then
      sellBuilding(gsm, nB, gsm.playerValues.resourceHolder.increase(profit), gsm.playerValues.capacity.decrease(cap).get, msg)
    else gsm.copy(message = msg)

  private def sellUnits(gsm: GameStateManager, units: List[IUnit], profit: ResourceHolder, cap: Capacity, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfUnits = units,
        resourceHolder = profit,
        capacity = cap),
      message = msg)

  private def sellBuilding(gsm: GameStateManager, building: List[IBuilding], profit: ResourceHolder, cap: Capacity, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfBuildings = building,
        resourceHolder = profit,
        capacity = cap),
      message = msg)

  def show(gsm: GameStateManager): GameStateManager =
    gsm.copy(message = GameStateStringFormatter(playerValues = gsm.playerValues, gameStateManager = gsm)
      .overview(gsm.round))
    
  def showMessage(gsm: GameStateManager, what: String): GameStateManager = gsm.copy(message = what)

  def move(gsm: GameStateManager, what: String, where: Coordinate): GameStateManager =
    gsm.copy(message = "move not implemented yet")

  def invalid(gsm: GameStateManager, input: String): GameStateManager =
    gsm.copy(message = f"$input - invalid\nEnter help to get an " +
      f"overview of all available commands")