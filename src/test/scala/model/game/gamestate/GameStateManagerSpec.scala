package model.game.gamestate

import model.game.*
import model.purchasable.types.EntityType
import model.purchasable.*
import model.game.gamestate.{GameStateStringFormatter, IGameStateManager}
import model.game.map.Coordinate
import model.purchasable.building.{Hangar, IBuilding, ResearchLab}
import model.purchasable.technology.{AdvancedMaterials, ITechnology, Polymer}
import model.purchasable.units.{Corvette, IUnit}
import model.resources.resourcetypes.{Energy, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*


class GameStateManagerSpec extends AnyWordSpec {

  "The GameStateManager" when {
    "initialized" should {
      val state: IGameStateManager = GameStateManager()
      "have a Round value of 1" in {
        state.round.value should be(1)
      }
      "have an Energy value of 100" in {
        state.playerValues.resourceHolder.energy.value should be(100)
      }
      "have a Mineral value of 100" in {
        state.playerValues.resourceHolder.minerals.value should be(100)
      }
      "have an Alloy value of 10" in {
        state.playerValues.resourceHolder.alloys.value should be(10)
      }
      "have a Research value of 100" in {
        state.playerValues.resourceHolder.researchPoints.value should be(100)
      }
      "have a GameState of type INIT" in {
        state.gameState should be(GameState.INIT)
      }
      "have a list of buildings" in {
        val gameStateManager = GameStateManager(playerValues = PlayerValues(listOfBuildings = List[IBuilding](ResearchLab())))
        gameStateManager.playerValues.listOfBuildings shouldBe a[List[_]]
        gameStateManager.playerValues.listOfBuildings should not be (empty)
        gameStateManager.playerValues.listOfBuildings.head shouldBe a[IBuilding]
      }
      "have a list of technologies" in {
        val gameStateManager = GameStateManager(playerValues = PlayerValues(listOfTechnologies = List[ITechnology](Polymer())))
        gameStateManager.playerValues.listOfTechnologies shouldBe a[List[_]]
        gameStateManager.playerValues.listOfTechnologies should not be (empty)
        gameStateManager.playerValues.listOfTechnologies.head shouldBe a[ITechnology]
      }
      "have a list of units" in {
        val gameStateManager = GameStateManager(playerValues = PlayerValues(listOfUnits = List[IUnit](Corvette())))
        gameStateManager.playerValues.listOfUnits shouldBe a[List[_]]
        gameStateManager.playerValues.listOfUnits should not be (empty)
        gameStateManager.playerValues.listOfUnits.head shouldBe a[IUnit]
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
      "update the game state and the string representation if research is invoked" in {
        state.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials").gameState should be(GameState.RUNNING)
        state.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials").toString should be("Researching: Advanced Materials")
      }
      "update the game state and the string representation if build is invoked" in {
        state.build(Hangar(), Hangar().cost, "Constructing: Hangar").gameState should be(GameState.RUNNING)
        state.build(Hangar(), Hangar().cost, "Constructing: Hangar").toString should be("Constructing: Hangar")
      }
      "update the game state and the string representation if recruit is invoked" in {
        state.recruit(Vector(Corvette()), Corvette().cost, "Recruiting: 1 x Corvette").gameState should be(GameState.RUNNING)
        state.recruit(Vector(Corvette()), Corvette().cost, "Recruiting: 1 x Corvette").toString should be("Recruiting: 1 x Corvette")
      }
      "update the game state and the string representation if show is invoked" in {
        state.show().gameState should be(GameState.RUNNING)
        state.show()
          .toString should be(GameStateStringFormatter(playerValues = state.playerValues).overview())
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
        tmpGameState.toString should be ("Are you sure? [yes (y) / no (n)]")
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
