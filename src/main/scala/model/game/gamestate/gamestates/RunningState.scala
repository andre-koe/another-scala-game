package model.game.gamestate.gamestates

import io.circe.*
import io.circe.generic.auto.*
import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector}
import model.core.board.boardutils.ICoordinate
import model.core.fileIO.IFileIOStrategy
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{ICapacity, IResourceHolder}
import model.game.gamestate.strategies.sell.{ISellStrategy, SellBuildingStrategy}
import model.game.gamestate.{GameStateStringFormatter, IGameState, IGameStateManager}
import model.game.playervalues.IPlayerValues

import scala.util.{Failure, Success}

case class RunningState() extends IGameState:

  def update(gsm: IGameStateManager): IGameStateManager = gsm.extCopy(message = "")


  def build(gsm: IGameStateManager, sector: IPlayerSector, nB: IResourceHolder, msg: String): IGameStateManager =
    val nPlayerValues = gsm.currentPlayerValues.extCopy(resourceHolder = nB)

    gsm.extCopy(message = msg,
      playerValues = updatePlayerValuesV(gsm, nPlayerValues),
      gameMap = gsm.gameMap.updateSector(sector))


  def research(gsm: IGameStateManager, tech: ITechnology, newBalance: IResourceHolder, msg: String): IGameStateManager =
    val nPlayerValues = gsm.currentPlayerValues.extCopy(
      listOfTechnologiesCurrentlyResearched = gsm.currentPlayerValues.listOfTechnologiesCurrentlyResearched.+:(tech),
      resourceHolder = newBalance)

    gsm.extCopy(message = msg, playerValues = updatePlayerValuesV(gsm, nPlayerValues))


  def recruit(gsm: IGameStateManager, sector: IPlayerSector, nB: IResourceHolder, nCap: ICapacity, msg: String): IGameStateManager =
    val nPlayerValues = gsm.currentPlayerValues.extCopy(resourceHolder = nB, capacity = nCap)
    gsm.extCopy(message = msg, playerValues = updatePlayerValuesV(gsm, nPlayerValues),
      gameMap = gsm.gameMap.updateSector(sector))


  def sell(gsm: IGameStateManager, sellStrategy: ISellStrategy): IGameStateManager = sellStrategy.sell(gsm)


  def save(gsm: IGameStateManager,
           fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager = fileIOStrategy.save(gsm, as)


  def load(gsm: IGameStateManager,
           fileIOStrategy: IFileIOStrategy, as: Option[String]): IGameStateManager =
    fileIOStrategy.load(as) match
      case Success(value) => value
      case _: Failure[IGameStateManager] => gsm.extCopy(message = s"Failed to load ${as.getOrElse("last savegame")}")


  def show(gsm: IGameStateManager): IGameStateManager = gsm.extCopy(message =
    GameStateStringFormatter(gsm = gsm).overview(gsm.round))


  def showMessage(gsm: IGameStateManager, what: String): IGameStateManager = gsm.extCopy(message = what)


  def move(gsm: IGameStateManager, what: String, where: ICoordinate): IGameStateManager =
    val optFleet = gsm.gameMap.data.flatMap(_.flatMap(_.unitsInSector)).find(_.name.toLowerCase == what)
    val optSector = optFleet.flatMap(fleet => gsm.gameMap.getSectorAtCoordinate(fleet.location))

    (optFleet, optSector) match
      case (Some(fleet), Some(sector)) =>
        val updatedFleet = fleet.extCopy(moveVector = MoveVector(fleet.location, where))
        val updatedUnitsInSector = sector.unitsInSector.map {
          case f if f.name == fleet.name => updatedFleet
          case f => f
        }
        val updatedSector = sector match {
          case pS: IPlayerSector => pS.extCopy(sector = pS.sector.cloneWith(unitsInSector = updatedUnitsInSector))
          case _ => sector.cloneWith(unitsInSector = updatedUnitsInSector)
        }
        gsm.extCopy(gameMap = gsm.gameMap.updateSector(updatedSector), message = s"${what.capitalize} en route to $where" )
      case _ => gsm.extCopy(message = "The specified fleet or sector doesn't exist")


  def invalid(gsm: IGameStateManager, input: String): IGameStateManager =
    gsm.extCopy(message = f"$input - invalid\nEnter help to get an " +
      f"overview of all available commands")


  private def updatePlayerValuesV(gsm: IGameStateManager, nPlayerValues: IPlayerValues): Vector[IPlayerValues] =
    val indexToUpdate = gsm.currentPlayerIndex
    gsm.playerValues.updated(indexToUpdate, nPlayerValues)