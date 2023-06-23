package model.game.gamestate

import io.circe.*
import io.circe.generic.auto.*
import model.core.board.boardutils.{GameBoardInfoWrapper, IGameBoardInfoWrapper}
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.mechanics.fleets.components.units.IUnit
import model.core.gameobjects.resources.IResource

class GameStateManagerWrapper(gsm: IGameStateManager) extends IGameStateManagerWrapper:
  override def getCurrentRound: Int = gsm.round.value

  override def getCapacity: Int = gsm.playerValues.capacity.value

  override def getPlayerTech: Vector[ITechnology] = gsm.playerValues.listOfTechnologies

  override def getPlayerTechCurrentlyResearch: Vector[ITechnology] = gsm.playerValues.listOfTechnologiesCurrentlyResearched

  override def getPlayerResources: Vector[IResource[_]] = gsm.playerValues.resourceHolder.resourcesAsVector

  override def getPlayerIncome: Vector[IResource[_]] = gsm.playerValues.income.resourcesAsVector

  override def getPlayerUpkeep: Vector[IResource[_]] = gsm.playerValues.upkeep.resourcesAsVector

  override def getPlayerNetIncome: Vector[IResource[_]] =
    gsm.playerValues.income.subtract(gsm.playerValues.upkeep).resourcesAsVector

  override def getGameMapInfo: IGameBoardInfoWrapper = GameBoardInfoWrapper(gsm.gameMap)

  override def getState: IGameState = gsm.gameState

  override def getResearchableTech: Vector[ITechnology] = gsm.getGameValues.tech

  override def getConstructableBuildings: Vector[IBuilding] = gsm.getGameValues.buildings

  override def getRecruitableUnits: Vector[IUnit] = gsm.getGameValues.units

  override def getGameMapSizeX: Int = getGameMapInfo.getSizeX

  override def getGameMapSizeY: Int = getGameMapInfo.getSizeY

  override def getGSM: IGameStateManager = gsm