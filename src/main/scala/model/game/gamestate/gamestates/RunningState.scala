package model.game.gamestate.gamestates

import io.circe.*
import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.IPlayerSector
import model.core.board.boardutils.ICoordinate
import model.core.fileIO.IFileIOStrategy
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ICapacity, IResourceHolder}
import model.game.gamestate.strategies.sell.{ISellStrategy, SellBuildingStrategy}
import model.game.gamestate.{GameStateStringFormatter, IGameState, IGameStateManager}

import scala.util.{Failure, Success}

case class RunningState() extends IGameState:

  def update(gsm: IGameStateManager): IGameStateManager = gsm.extCopy(message = "")

  def build(gsm: IGameStateManager, sector: IPlayerSector, nB: IResourceHolder, msg: String): IGameStateManager =
    gsm.extCopy(message = msg, playerValues = gsm.playerValues.extCopy(resourceHolder = nB),
      gameMap = gsm.gameMap.updateSector(sector)
    )

  def research(gsm: IGameStateManager, tech: ITechnology, newBalance: IResourceHolder, msg: String): IGameStateManager =
    gsm.extCopy(message = msg, playerValues = gsm.playerValues.extCopy(
      listOfTechnologiesCurrentlyResearched = gsm.playerValues.listOfTechnologiesCurrentlyResearched.+:(tech),
      resourceHolder = newBalance))

  def recruit(gsm: IGameStateManager, sector: IPlayerSector, nB: IResourceHolder, nCap: ICapacity, msg: String): IGameStateManager =
    gsm.extCopy(message = msg, playerValues = gsm.playerValues.extCopy(
      resourceHolder = nB,
      capacity = nCap),
      gameMap = gsm.gameMap.updateSector(sector)
    )

  def sell(gsm: IGameStateManager, sellStrategy: ISellStrategy): IGameStateManager = sellStrategy.sell(gsm)

  def save(gsm: IGameStateManager,
           fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager = fileIOStrategy.save(gsm, as)

  def load(gsm: IGameStateManager,
           fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager =
    fileIOStrategy.load(as) match
      case Success(value) => value
      case e: Failure[IGameStateManager] =>
        println(s"An error occurred: $e")
        gsm.extCopy(message = s"Failed to load ${as.getOrElse("last savegame")}")
  def show(gsm: IGameStateManager): IGameStateManager = gsm.extCopy(message =
    GameStateStringFormatter(gsm = gsm).overview(gsm.round))
    
  def showMessage(gsm: IGameStateManager, what: String): IGameStateManager = gsm.extCopy(message = what)

  def move(gsm: IGameStateManager, what: String, where: ICoordinate): IGameStateManager =
    gsm.extCopy(message = "move not implemented yet")

  def invalid(gsm: IGameStateManager, input: String): IGameStateManager =
    gsm.extCopy(message = f"$input - invalid\nEnter help to get an " +
      f"overview of all available commands")