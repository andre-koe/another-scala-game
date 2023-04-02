package model.game.gamestate

import model.countable.{Balance, Research}
import model.game.{Coordinate, IValues, Round}
import model.purchasable.types.EntityType

trait IGameStateManager {
  def playerValues: IValues
  def round: Round
  def funds: Balance
  def researchOutput: Research
  def gameState: GameState
  def build(what: String): IGameStateManager
  def research(what: String): IGameStateManager
  def recruit(what: String, howMany: Int): IGameStateManager
  def sell(what: String, howMany: Int): IGameStateManager
  def list(what: Option[EntityType]): IGameStateManager
  def show(): IGameStateManager
  def help(what: Option[String]): IGameStateManager
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
