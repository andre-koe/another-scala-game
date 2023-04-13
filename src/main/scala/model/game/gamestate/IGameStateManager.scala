package model.game.gamestate

import model.game.map.Coordinate
import model.game.{Capacity, IValues, PlayerValues, Round}
import model.purchasable.IUpkeep
import model.purchasable.building.IBuilding
import model.purchasable.technology.ITechnology
import model.purchasable.types.EntityType
import model.purchasable.units.IUnit
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Energy, ResearchPoints}

trait IGameStateManager {
  def round: Round
  def playerValues: PlayerValues
  def gameState: GameState
  def build(building: IBuilding, newBalance: ResourceHolder, msg: String): IGameStateManager
  def research(technology: ITechnology, newBalance: ResourceHolder, msg: String): IGameStateManager
  def recruit(what: Vector[IUnit], newBalance: ResourceHolder, msg: String): IGameStateManager
  def sell(newUnits: Option[List[IUnit]],
           newBuildings: Option[List[IBuilding]],
           profit: ResourceHolder,
           capacity: Capacity,
           savedUpkeep: ResourceHolder,
           message: String): IGameStateManager
  def show(): IGameStateManager
  def move(what: String, where: Coordinate): IGameStateManager
  def invalid(input: String): IGameStateManager
  def endRoundRequest(): IGameStateManager
  def endRoundConfirmation(): IGameStateManager
  def resetGameState(): IGameStateManager
  def exit(): IGameStateManager
  def save(as: Option[String]): IGameStateManager
  def load(as: Option[String]): IGameStateManager
  def empty(): IGameStateManager
  def message(what: String): IGameStateManager
}
