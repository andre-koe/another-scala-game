package model.game.gamestate

import GameState.*
import controller.playeractions.ActionType
import model.countable.{Balance, Research}
import model.game.{Coordinate, GameValues, IValues, PlayerValues, Round}
import model.purchasable.building.{Building, BuildingFactory, EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer, Technology, TechnologyFactory}
import model.purchasable.types.EntityType
import model.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, Ship, UnitFactory}

import scala.+:
import scala.compiletime.ops.string

case class GameStateManager(round: Round = Round(),
                            funds: Balance = Balance(),
                            researchOutput: Research = Research(),
                            message: String = "",
                            playerValues: IValues = PlayerValues(),
                            gameState: GameState = GameState.INIT) extends IGameStateManager {


  private val gameValues: IValues = GameValues()

  private def nextRound: GameStateManager =
    val newRound = round.next
    //val newFunds = funds.increase(calculateBalance())
    //val newResearch = researchOutput.increase(calculateResearch())

    this.copy(
      gameState = GameState.RUNNING,
      round = newRound,
      funds = Balance(110),
      researchOutput = researchOutput,
      message = GameStateStringFormatter(round = newRound, funds = funds, researchOutput = researchOutput).overview()
    )

  // private def calculateBalance(): Balance = ???
  // private def calculateResearch(): Research = ???

  override def toString: String = message

  override def build(what: String): IGameStateManager =
    val building: Building = BuildingFactory().create(what.toLowerCase).get
    this.copy(
      gameState = RUNNING,
      playerValues = PlayerValues(listOfBuildings = playerValues.listOfBuildings.+:(building)),
      message = s"Constructing: ${building.name}")

  override def research(what: String): IGameStateManager =
    val tech: Technology = TechnologyFactory().create(what.toLowerCase).get
    this.copy(
      gameState = RUNNING,
      playerValues = PlayerValues(listOfTechnologies = playerValues.listOfTechnologies.+:(tech)),
      message = s"Researching: ${tech.name}")

  override def recruit(what: String, howMany: Int): IGameStateManager =
    val unit: Ship = UnitFactory().create(what.toLowerCase, howMany).get
    this.copy(
      gameState = RUNNING,
      playerValues = PlayerValues(listOfUnits = playerValues.listOfUnits.+:(unit)),
      message = s"Recruiting: $howMany x ${unit.name}")

  override def sell(what: String, howMany: Int): IGameStateManager =
    this.copy(gameState = RUNNING, message = "sell not implemented yet")

  override def list(what: Option[EntityType]): IGameStateManager =
    what match
      case None => this.copy(gameState = RUNNING, message = GameStateStringFormatter().listAll)
      case Some(EntityType.TECHNOLOGY) =>
        this.copy(gameState = RUNNING, message = GameStateStringFormatter().listTechnologies)
      case Some(EntityType.UNIT) =>
        this.copy(gameState = RUNNING, message = GameStateStringFormatter().listUnits)
      case Some(EntityType.BUILDING) =>
        this.copy(gameState = RUNNING, message = GameStateStringFormatter().listBuildings)
  override def show(): IGameStateManager =
    this.copy(gameState = RUNNING,
      message = GameStateStringFormatter().overview(round, funds, researchOutput))
  override def help(what: Option[String]): IGameStateManager =
    what match
      case Some("building") =>
        this.copy(gameState = RUNNING,
          message = "A building can impact the game in various ways, such as increasing research output, " +
          "providing energy, or increasing unit capacity.")
      case Some("technology") =>
        this.copy(gameState = RUNNING,
          message = "A technology in the game that can be researched by " +
            "players to unlock new abilities, units, or buildings.")
      case Some("unit") =>
        this.copy(gameState = RUNNING, message = "A unit can be used to fight over sectors and conquer new sectors.")
      case None =>
        this.copy(gameState = RUNNING, message = GameStateStringFormatter().helpResponse)
      case _ =>
        this.copy(gameState = RUNNING, message = findInLists(what.get))

  override def move(what: String, where: Coordinate): IGameStateManager =
    this.copy(gameState = RUNNING, message = "move not implemented yet")
  override def invalid(input: String): IGameStateManager =
    this.copy(gameState = RUNNING, message = GameStateStringFormatter().invalidInputResponse(input))
  override def endRoundRequest(): IGameStateManager =
    this.copy(gameState = END_ROUND_REQUEST, message = GameStateStringFormatter().askForConfirmation)
  override def endRoundConfirmation(): IGameStateManager =
    if (gameState == END_ROUND_REQUEST) nextRound
    else this.copy(gameState = RUNNING, message = GameStateStringFormatter().empty)
  override def resetGameState(): IGameStateManager =
    this.copy(gameState = RUNNING, message = GameStateStringFormatter().empty)
  override def exit(): IGameStateManager =
    this.copy(gameState = EXITED, message = GameStateStringFormatter().goodbyeResponse)
  override def save(as: Option[String]): IGameStateManager =
    this.copy(gameState = RUNNING, message = "save not implemented yet")
  override def load(as: Option[String]): IGameStateManager =
    this.copy(gameState = RUNNING, message = "load not implemented yet")
  override def message(what: String): IGameStateManager =
    this.copy(gameState = RUNNING, message = GameStateStringFormatter(userMsg = what).showMessage)
  override def empty(): IGameStateManager =
    this.copy(gameState = RUNNING, message = GameStateStringFormatter().empty)
  private def findInLists(str: String): String =
    if (gameValues.listOfTechnologies.exists(_.name.toLowerCase == str))
      gameValues.listOfTechnologies.find(_.name.toLowerCase == str).get.toString
    else if (gameValues.listOfBuildings.exists(_.name.toLowerCase() == str))
      gameValues.listOfBuildings.find(_.name.toLowerCase() == str).get.toString
    else if (gameValues.listOfUnits.exists(_.name.toLowerCase() == str))
      gameValues.listOfUnits.find(_.name.toLowerCase() == str).get.toString
    else
      s"Could not find any information on '$str'"
}