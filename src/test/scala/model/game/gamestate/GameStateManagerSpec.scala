package model.game.gamestate

import controller.playeractions.ActionType
import model.countable.{Balance, Research}
import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GameStateManagerSpec extends AnyWordSpec {

  "The GameStateManager" when {
    "initialized" should {
      val state: GameStateManager = GameStateManager()
      "have a Round value of 1" in {
        state.round.value should be (1)
      }
      "have a Funds value of 1000" in {
        state.funds.value should be (1000)
      }
      "have a Research value of 100" in {
        state.researchOutput.value should be (100)
      }
      "have a GameState of type INIT" in {
        state.gameState should be (GameState.INIT)
      }
    }
    "handling a player action" should {
      val state: GameStateManager = GameStateManager()

      val actionEmpty: ActionType = ActionType.EMPTY
      val actionExit: ActionType = ActionType.EXIT
      val actionHelp: ActionType = ActionType.HELP
      val actionMove: ActionType = ActionType.MOVE(Option("x"), Option("y"))
      val actionSave: ActionType = ActionType.SAVE(Option("test"))
      val actionLoad: ActionType = ActionType.LOAD(Option("test"))
      val actionShowBuildings: ActionType = ActionType.SHOW(Option("buildings"))
      val actionShowTech: ActionType = ActionType.SHOW(Option("technology"))
      val actionShowOverview: ActionType = ActionType.SHOW(Option("overview"))
      val actionShowUnits: ActionType = ActionType.SHOW(Option("units"))
      val actionListBuildings: ActionType = ActionType.LIST(Option("buildings"))
      val actionListTech: ActionType = ActionType.LIST(Option("technology"))
      val actionListUnits: ActionType = ActionType.LIST(Option("units"))
      val actionBuild: ActionType = ActionType.BUILD(Option("something"))
      val actionResearch: ActionType = ActionType.RESEARCH(Option("something"))
      val actionRecruit: ActionType = ActionType.RECRUIT(Option("something"), Option("something"))
      val actionSell: ActionType = ActionType.SELL(Option("something"))
      val actionInvalid: ActionType = ActionType.INVALID(Option("something"))

      // Invalid
      val invalidList: ActionType = ActionType.LIST(Option("shrimp"))
      val invalidShow: ActionType = ActionType.SHOW(Option("shrimp"))

      "update the game state and the string representation according to the move Action" in {
        state.handlePlayerAction(actionMove).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionMove).toString() should be("moving to x, y")
      }
      "update the game state and the string representation according to the save Action" in {
        state.handlePlayerAction(actionSave).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionSave).toString() should be("saving test")
      }
      "update the game state and the string representation according to the load Action" in {
        state.handlePlayerAction(actionLoad).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionLoad).toString() should be("loading test")
      }
      "update the game state and the string representation according to the empty Action" in {
        state.handlePlayerAction(actionEmpty).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionEmpty).toString() should be("")
      }
      "update the game state and the string representation according to the exit Action" in {
        state.handlePlayerAction(actionExit).gameState should be (GameState.EXITED)
        state.handlePlayerAction(actionExit).toString() should be (GameStateStringFormatter().goodbyeResponse)
      }
      "update the game state and the string representation according to the help Action" in {
        state.handlePlayerAction(actionHelp).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionHelp).toString() should be (GameStateStringFormatter().helpResponse)
      }
      "update the game state and the string representation according to the research Action" in {
        state.handlePlayerAction(actionResearch).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionResearch).toString() should be("researching something")
      }
      "update the game state and the string representation according to the build Action" in {
        state.handlePlayerAction(actionBuild).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionBuild).toString() should be("constructing something")
      }
      "update the game state and the string representation according to the recruit Action" in {
        state.handlePlayerAction(actionRecruit).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionRecruit).toString() should be("recruiting something")
      }
      "update the game state and the string representation according to the show (building) Action" in {
        state.handlePlayerAction(actionShowBuildings).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionShowBuildings).toString() should be("some buildings")
      }
      "update the game state and the string representation according to the show (units) Action" in {
        state.handlePlayerAction(actionShowUnits).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionShowUnits).toString() should be("some units")
      }
      "update the game state and the string representation according to the show (technology) Action" in {
        state.handlePlayerAction(actionShowTech).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionShowTech).toString() should be("some technology")
      }
      "update the game state and the string representation according to the show (overview) Action" in {
        state.handlePlayerAction(actionShowOverview).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionShowOverview).toString() should be(GameStateStringFormatter().overview())
      }
      "update the game state and the string representation according to an invalid show Action" in {
        state.handlePlayerAction(invalidShow).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(invalidShow)
          .toString() should be("show 'shrimp' doesn't exist try show <buildings | technology | units | overview>" +
          "\nEnter help to get an overview of all available commands")
      }
      "update the game state and the string representation according to the list (building) Action" in {
        state.handlePlayerAction(actionListBuildings).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionListBuildings).toString() should be("some buildings")
      }
      "update the game state and the string representation according to the list (units) Action" in {
        state.handlePlayerAction(actionListUnits).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionListUnits).toString() should be("some units")
      }
      "update the game state and the string representation according to the list (technology) Action" in {
        state.handlePlayerAction(actionListTech).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionListTech).toString() should be("some technology")
      }
      "update the game state and the string representation according to an invalid list Action" in {
        state.handlePlayerAction(invalidList).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(invalidList)
          .toString() should be("list 'shrimp' doesn't exist try show <buildings | technology | units>" +
          "\nEnter help to get an overview of all available commands")
      }
      "update the game state and the string representation according to the sell Action" in {
        state.handlePlayerAction(actionSell).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionSell).toString() should be("sold something")
      }
      "update the game state and the string representation according to an invalid Action" in {
        state.handlePlayerAction(actionInvalid).gameState should be(GameState.RUNNING)
        state.handlePlayerAction(actionInvalid)
          .toString() should be(GameStateStringFormatter().invalidInputResponse("something"))
      }
    }
    "handling the [end round] mechanic" should {
      val actionFinishRound: ActionType = ActionType.FINISH_ROUND_REQUEST
      val actionUserDecline: ActionType = ActionType.USER_DECLINE
      val actionUserAccept: ActionType = ActionType.USER_ACCEPT

      val tmpGameState: GameStateManager = GameStateManager().handlePlayerAction(actionFinishRound)
      "prompt the user for input and wait if end round action was triggered" in {
        tmpGameState.gameState should be (GameState.END_ROUND_REQUEST)
        tmpGameState.toString() should be (GameStateStringFormatter().askForConfirmation)
      }
      "continue if the user declines after the prompt or enters something else" in {
        var endRoundGameState: GameStateManager = tmpGameState
        endRoundGameState = endRoundGameState.handlePlayerAction(actionUserDecline)
        endRoundGameState.gameState should be (GameState.RUNNING)
        endRoundGameState.round.value should be (1)

        endRoundGameState = tmpGameState
        endRoundGameState = endRoundGameState.handlePlayerAction(actionFinishRound)
        endRoundGameState.gameState should be (GameState.END_ROUND_REQUEST)
        endRoundGameState.round.value should be (1)
      }
      "end the round if the user accepts after the prompt" in {
        var endRoundGameState: GameStateManager = tmpGameState
        endRoundGameState = endRoundGameState.handlePlayerAction(actionUserAccept)
        endRoundGameState.gameState should be(GameState.RUNNING)
        endRoundGameState.round.value should be(2)
      }
    }
  }
}
