package model.game.gamestate.gamestates

import model.game.Capacity
import model.game.gamestate.strategies.sell.{ISellStrategy, SellBuildingStrategy}
import model.game.gamestate.{GameStateManager, GameStateStringFormatter, IGameState}
import model.game.map.Coordinate
import model.game.purchasable.building.IBuilding
import model.game.purchasable.technology.ITechnology
import model.game.purchasable.units.IUnit
import model.game.resources.ResourceHolder

case class RunningState() extends IGameState:

  def update(gsm: GameStateManager): GameStateManager = gsm

  def build(gsm: GameStateManager, building: IBuilding, nB: ResourceHolder, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfBuildingsUnderConstruction = gsm.playerValues.listOfBuildingsUnderConstruction.+:(building),
        resourceHolder = nB),
      message = msg)

  def research(gsm: GameStateManager, tech: ITechnology, newBalance: ResourceHolder, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfTechnologiesCurrentlyResearched = gsm.playerValues.listOfTechnologiesCurrentlyResearched.+:(tech),
        resourceHolder = newBalance),
      message = msg)

  def recruit(gsm: GameStateManager, what: Vector[IUnit], nB: ResourceHolder, nCap: Capacity, msg: String): GameStateManager =
    gsm.copy(
      playerValues = gsm.playerValues.copy(
        listOfUnitsUnderConstruction = gsm.playerValues.listOfUnitsUnderConstruction.++:(what.toList),
        resourceHolder = nB,
        capacity = nCap
      ),
      message = msg)

  def sell(gsm: GameStateManager, sellStrategy: ISellStrategy, msg: String): GameStateManager =
    sellStrategy.sell(gsm).copy(message = msg)

  def show(gsm: GameStateManager): GameStateManager =
    gsm.copy(message = GameStateStringFormatter(playerValues = gsm.playerValues, gameStateManager = gsm)
      .overview(gsm.round))
    
  def showMessage(gsm: GameStateManager, what: String): GameStateManager = gsm.copy(message = what)

  def move(gsm: GameStateManager, what: String, where: Coordinate): GameStateManager =
    gsm.copy(message = "move not implemented yet")

  def invalid(gsm: GameStateManager, input: String): GameStateManager =
    gsm.copy(message = f"$input - invalid\nEnter help to get an " +
      f"overview of all available commands")