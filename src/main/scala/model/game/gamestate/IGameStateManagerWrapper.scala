package model.game.gamestate

import model.core.board.boardutils.IGameBoardInfoWrapper
import model.core.gameobjects.purchasable.building.IBuilding
import model.core.gameobjects.purchasable.technology.ITechnology
import model.core.gameobjects.resources.IResource
import model.core.mechanics.fleets.components.units.IUnit

trait IGameStateManagerWrapper:

  def getCurrentRound: Int
  
  def getCapacity: Int

  def getPlayerTech: Vector[ITechnology]

  def getPlayerTechCurrentlyResearch: Vector[ITechnology]

  def getPlayerResources: Vector[IResource[_]]

  def getPlayerIncome: Vector[IResource[_]]

  def getPlayerUpkeep: Vector[IResource[_]]

  def getPlayerNetIncome: Vector[IResource[_]]

  def getGameMapInfo: IGameBoardInfoWrapper

  def getState: IGameState

  def getResearchableTech: Vector[ITechnology]

  def getConstructableBuildings: Vector[IBuilding]

  def getRecruitableUnits: Vector[IUnit]

  def getGameMapSizeX: Int

  def getGameMapSizeY: Int

  def getGSM: IGameStateManager



