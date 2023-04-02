package model.game.gamestate

import model.countable.{Balance, Research}
import model.game.*
import model.purchasable.types.EntityType
import model.purchasable.*
import model.game.gamestate.{GameStateStringFormatter, IGameStateManager}
import model.purchasable.building.{Building, ResearchLab}
import model.purchasable.technology.{Polymer, Technology}
import model.purchasable.units.{Corvette, Ship}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*


class GameStateManagerSpec extends AnyWordSpec {

  "The GameStateManager" when {
    "initialized" should {
      val state: IGameStateManager = GameStateManager()
      "have a Round value of 1" in {
        state.round.value should be(1)
      }
      "have a Funds value of 1000" in {
        state.funds.value should be(1000)
      }
      "have a Research value of 100" in {
        state.researchOutput.value should be(100)
      }
      "have a GameState of type INIT" in {
        state.gameState should be(GameState.INIT)
      }
      "have a list of buildings" in {
        val gameStateManager = GameStateManager(playerValues = PlayerValues(listOfBuildings = List[Building](ResearchLab())))
        gameStateManager.playerValues.listOfBuildings shouldBe a[List[_]]
        gameStateManager.playerValues.listOfBuildings should not be (empty)
        gameStateManager.playerValues.listOfBuildings.head shouldBe a[Building]
      }
      "have a list of technologies" in {
        val gameStateManager = GameStateManager(playerValues = PlayerValues(listOfTechnologies = List[Technology](Polymer())))
        gameStateManager.playerValues.listOfTechnologies shouldBe a[List[_]]
        gameStateManager.playerValues.listOfTechnologies should not be (empty)
        gameStateManager.playerValues.listOfTechnologies.head shouldBe a[Technology]
      }
      "have a list of units" in {
        val gameStateManager = GameStateManager(playerValues = PlayerValues(listOfUnits = List[Ship](Corvette())))
        gameStateManager.playerValues.listOfUnits shouldBe a[List[_]]
        gameStateManager.playerValues.listOfUnits should not be (empty)
        gameStateManager.playerValues.listOfUnits.head shouldBe a[Ship]
      }
    }
    "handling a player command" should {
      val state: IGameStateManager = GameStateManager()

      "update the game state and the string representation if move is invoked" in {
        state.move("", Coordinate()).gameState should be(GameState.RUNNING)
        state.move("",Coordinate()).toString should be("move not implemented yet")
      }
      "update the game state and the string representation if save is invoked" in {
        state.save(Option("test")).gameState should be(GameState.RUNNING)
        state.save(Option("test")).toString should be("save not implemented yet")
      }
      "update the game state and the string representation if load is invoked" in {
        state.load(Option("test")).gameState should be(GameState.RUNNING)
        state.load(Option("test")).toString should be("load not implemented yet")
      }
      "update the game state and the string representation if empty is invoked" in {
        state.empty().gameState should be(GameState.RUNNING)
        state.empty().toString should be("")
      }
      "update the game state and the string representation if exit is invoked" in {
        state.exit().gameState should be (GameState.EXITED)
        state.exit().toString should be (GameStateStringFormatter().goodbyeResponse)
      }
      "update the game state and the string representation if help is invoked" in {
        state.help(None).gameState should be(GameState.RUNNING)
        state.help(None).toString should be (GameStateStringFormatter().helpResponse)
      }
      "update the game state and the string representation if research is invoked" in {
        state.research("Advanced Materials").gameState should be(GameState.RUNNING)
        state.research("Advanced Materials").toString should be("Researching: Advanced Materials")
      }
      "update the game state and the string representation if build is invoked" in {
        state.build("Hangar").gameState should be(GameState.RUNNING)
        state.build("Hangar").toString should be("Constructing: Hangar")
      }
      "update the game state and the string representation if recruit is invoked" in {
        state.recruit("Corvette", 2).gameState should be(GameState.RUNNING)
        state.recruit("Corvette", 2).toString should be("Recruiting: 2 x Corvette")
      }
      "update the game state and the string representation if show is invoked" in {
        state.show().gameState should be(GameState.RUNNING)
        state.show().toString should be(GameStateStringFormatter().overview())
      }
      "update the game state and the string representation if list (None) is invoked" in {
        state.list(None).gameState should be(GameState.RUNNING)
        state.list(None).toString should be(GameStateStringFormatter().listAll)
      }
      "update the game state and the string representation if list (building) is invoked" in {
        state.list(Option(EntityType.BUILDING)).gameState should be(GameState.RUNNING)
        state.list(Option(EntityType.BUILDING)).toString should be(GameStateStringFormatter().listBuildings)
      }
      "update the game state and the string representation if list (technology) is invoked" in {
        state.list(Option(EntityType.TECHNOLOGY)).gameState should be(GameState.RUNNING)
        state.list(Option(EntityType.TECHNOLOGY)).toString should be(GameStateStringFormatter().listTechnologies)
      }
      "update the game state and the string representation if list (units) is invoked" in {
        state.list(Option(EntityType.UNIT)).gameState should be(GameState.RUNNING)
        state.list(Option(EntityType.UNIT)).toString should be(GameStateStringFormatter().listUnits)
      }
      "update the game state and the string representation if sell is invoked" in {
        state.sell("something", 1).gameState should be(GameState.RUNNING)
        state.sell("something", 1).toString should be("sell not implemented yet")
      }
      "update the game state and the string if an invalid command is invoked" in {
        state.invalid("testst").gameState should be(GameState.RUNNING)
        state.invalid("testst")
          .toString should be(GameStateStringFormatter().invalidInputResponse("testst"))
      }
    }
    "handling the [end round] mechanic" should {
      val tmpGameState: IGameStateManager = GameStateManager().endRoundRequest()
      "prompt the user for input and wait if end round action was triggered" in {
        tmpGameState.gameState should be (GameState.END_ROUND_REQUEST)
        tmpGameState.toString should be (GameStateStringFormatter().askForConfirmation)
      }
      "end the round if the user accepts after the prompt" in {
        var endRoundGameState: IGameStateManager = tmpGameState
        endRoundGameState = endRoundGameState.endRoundConfirmation()
        endRoundGameState.gameState should be(GameState.RUNNING)
        endRoundGameState.round.value should be(2)
      }
    }
  }
 }
