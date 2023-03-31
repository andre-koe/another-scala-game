package model.game.gamestate

import GameState._
import controller.playeractions.ActionType
import model.countable.{Balance, Research}
import model.game.Round

case class GameStateManager(round: Round = Round(),
                            funds: Balance = Balance(),
                            researchOutput: Research = Research(),
                            message: String = "",
                            gameState: GameState = GameState.INIT) {
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
  
  def handlePlayerAction(action: ActionType): GameStateManager = action match
    case ActionType.EMPTY => this.copy(gameState = GameState.RUNNING, message = GameStateStringFormatter().empty)
    case ActionType.EXIT => handleExitAction()
    case ActionType.HELP => handleHelpAction()
    case ActionType.FINISH_ROUND_REQUEST => handleFinishRoundRequest()
    case ActionType.BUILD(value) => handleBuildAction(value)
    case ActionType.RESEARCH(value) => handleResearchAction(value)
    case ActionType.RECRUIT(value1, value2) => handleRecruitAction(value1, value2)
    case ActionType.SHOW(value) => handleShowAction(value)
    case ActionType.LIST(value) => handleListAction(value)
    case ActionType.SELL(value) => handleSellAction(value)
    case ActionType.INVALID(value) => handleInvalidAction(value)
    case ActionType.MOVE(x, y) => handleMoveAction(x, y)
    case ActionType.SAVE(value) => handleSaveAction(value)
    case ActionType.LOAD(value) => handleLoadAction(value)
    case ActionType.USER_ACCEPT | ActionType.USER_DECLINE => handleUserConfirmation(action)


  private def handleExitAction(): GameStateManager =
    this.copy(gameState = GameState.EXITED, message = GameStateStringFormatter().goodbyeResponse)

  private def handleInvalidAction(value: Option[String]): GameStateManager =
    this.copy(gameState = GameState.RUNNING, message = GameStateStringFormatter().invalidInputResponse(value.get))

  private def handleHelpAction(): GameStateManager =
    this.copy(gameState = GameState.RUNNING, message = GameStateStringFormatter().helpResponse)

  private def handleShowAction(what: Option[String]): GameStateManager =
    what.get match
      case "buildings" => this.copy(gameState = GameState.RUNNING, message = s"some buildings")
      case "technology" => this.copy(gameState = GameState.RUNNING, message = s"some technology")
      case "units" => this.copy(gameState = GameState.RUNNING, message = s"some units")
      case "overview" =>
        this.copy(gameState = GameState.RUNNING,
          message = GameStateStringFormatter().overview(round, funds, researchOutput))
      case other =>
        this.copy(gameState = GameState.RUNNING,
          message = GameStateStringFormatter()
            .invalidInputResponse(s"show '$other' doesn't exist try show <buildings | technology | units | overview>"))

  private def handleListAction(what: Option[String]): GameStateManager =
    what.get match
      case "buildings" => this.copy(gameState = GameState.RUNNING, message = s"some buildings")
      case "technology" => this.copy(gameState = GameState.RUNNING, message = s"some technology")
      case "units" => this.copy(gameState = GameState.RUNNING, message = s"some units")
      case other =>
        this.copy(gameState = GameState.RUNNING,
          message = GameStateStringFormatter()
            .invalidInputResponse(s"list '$other' doesn't exist try show <buildings | technology | units>"))

  private def handleSellAction(what: Option[String]): GameStateManager =
    this.copy(gameState = RUNNING, message = s"sold ${what.get}")

  private def handleFinishRoundRequest(): GameStateManager =
    this.copy(gameState = END_ROUND_REQUEST, message = GameStateStringFormatter().askForConfirmation)

  private def handleUserConfirmation(action: ActionType): GameStateManager =
    if gameState != END_ROUND_REQUEST
    then this.copy(
      gameState = RUNNING,
      message = GameStateStringFormatter().invalidInputResponse("You must end the round by entering [done] first")
    )
    else
      action match
        case ActionType.USER_ACCEPT => nextRound
        case _ => this.copy(gameState = RUNNING, message = GameStateStringFormatter().empty)


  // TODO: Implement Methods
  private def handleSaveAction(maybeString: Option[String]) =
    this.copy(gameState = RUNNING, message = s"saving ${maybeString.get}")
  private def handleLoadAction(maybeString: Option[String]) =
    this.copy(gameState = RUNNING, message = s"loading ${maybeString.get}")
  private def handleMoveAction(maybeString: Option[String], maybeString1: Option[String]) =
    this.copy(gameState = RUNNING, message = s"moving to ${maybeString.get}, ${maybeString1.get}")
  private def handleBuildAction(maybeString: Option[String]) =
    this.copy(gameState = RUNNING, message = s"constructing ${maybeString.get}")
  private def handleRecruitAction(maybeString: Option[String], quantity: Option[String]) =
    this.copy(gameState = RUNNING, message = s"recruiting ${maybeString.get}")
  private def handleResearchAction(maybeString: Option[String]) =
    this.copy(gameState = RUNNING, message = s"researching ${maybeString.get}")


  // private def calculateBalance(): Balance = ???
  // private def calculateResearch(): Research = ???

  override def toString: String = message
}
