package model.game.gamestate

import model.game.*
import model.game.gamestate.GameStateStringFormatter
import model.game.gamestate.gamestates.{EndRoundConfirmationState, ExitedState, RunningState, WaitForUserConfirmation}
import model.game.gamestate.strategies.sell.SellBuildingStrategy
import model.game.map.Coordinate
import model.game.purchasable.building.{Hangar, IBuilding, Mine, ResearchLab}
import model.game.purchasable.technology.{AdvancedMaterials, ITechnology, Polymer}
import model.game.purchasable.types.EntityType
import model.game.purchasable.units.{Corvette, IUnit}
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*


class GameStateManagerSpec extends AnyWordSpec {

  "The GameStateManager" when {
    "in state Running (Initialized)" should {
      val playerVal: PlayerValues =
        PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100),
          minerals = Minerals(100),
          alloys = Alloys(10),
          researchPoints = ResearchPoints(100)
        ), capacity = Capacity(20))
      val state: GameStateManager = GameStateManager(playerValues = playerVal)
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
      val state: GameStateManager = GameStateManager()

      "update the game state and the string representation if move is invoked" in {
        state.move("",Coordinate()).toString should be("move not implemented yet")
      }
      "update the game state and the string representation if save is invoked" in {
        state.save(Option("test")).toString should be("save not implemented yet")
      }
      "update the game state and the string representation if load is invoked" in {
        state.load(Option("test")).toString should be("load not implemented yet")
      }
      "update the game state and the string representation if empty is invoked" in {
        state.empty().toString should be("")
      }
      "update the game state and the string representation if exit is invoked" in {
        state.exit().toString should be (GameStateStringFormatter(gameStateManager = state).goodbyeResponse)
      }
      "update the game state and the string representation if research is invoked" in {
        state.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials").toString should be("Researching: Advanced Materials")
      }
      "update the game state and the string representation if build is invoked" in {
        state.build(Hangar(), Hangar().cost, "Constructing: Hangar").toString should be("Constructing: Hangar")
      }
      "update the game state and the string representation if recruit is invoked" in {
        state.recruit(Vector(Corvette()), Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
        state.recruit(Vector(Corvette()), Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
          .toString should be("Recruiting: 1 x Corvette")
      }
      "update the game state and the string representation if show is invoked" in {
        state.show()
          .toString should be(GameStateStringFormatter(playerValues = state.playerValues,
          gameStateManager = state).overview())
      }
      "update the game state and the string if an invalid command is invoked" in {
        state.invalid("testst")
          .toString should be(GameStateStringFormatter(gameStateManager = state).invalidInputResponse("testst"))
      }
    }
    "handling the [end round] mechanic" should {
      val tmpGameState: GameStateManager = GameStateManager().endRoundRequest()
      "prompt the user for input and wait if end round action was triggered" in {
        tmpGameState.toString should be ("Are you sure? [yes (y) / no (n)]")
      }
      "end the round if the user accepts after the prompt" in {
        var endRoundGameState: GameStateManager = tmpGameState
        endRoundGameState = endRoundGameState.accept()
        endRoundGameState.round.value should be(2)
      }
    }
  }
  "When in GameState WaitForUserConfirmation" should {
    val playerVal: PlayerValues =
      PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100),
        minerals = Minerals(100),
        alloys = Alloys(10),
        researchPoints = ResearchPoints(100)
      ), capacity = Capacity(20))

    val state: GameStateManager = GameStateManager(gameState = WaitForUserConfirmation(), playerValues = playerVal)

    "not update the game state and the string representation if move is invoked" in {
      state.move("", Coordinate()).toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if save is invoked" in {
      state.save(Option("test")).toString should be("Invalid")
    }
    "not update the game state and the string representation if load is invoked" in {
      state.load(Option("test")).toString should be("Invalid")
    }
    "not update the game state and the string representation if empty is invoked" in {
      state.empty().toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if exit is invoked" in {
      state.exit().toString should be(GameStateStringFormatter().goodbyeResponse)
    }
    "not update the game state and the string representation if research is invoked" in {
      state.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials")
        .toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if build is invoked" in {
      state.build(Hangar(), Hangar().cost, "Constructing: Hangar")
        .toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if recruit is invoked" in {
      state.recruit(Vector(Corvette()), Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
        .toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if show is invoked" in {
      state.show().toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string if an invalid command is invoked" in {
      state.invalid("testst").toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state if a message command is invoked" in {
      state.message("something").toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state if a sell command is invoked" in {
      state.sell(SellBuildingStrategy(List(Mine()), ResourceHolder(), Capacity()), "sell")
        .toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "update the game state if a accept is invoked" in {
      state.accept().round.value should be(2)
      state.accept().toString() should be(GameStateStringFormatter(playerValues = playerVal)
        .overview(state.round.next, state.playerValues.resourceHolder))
    }
    "update the game state if a decline is invoked" in {
      state.decline().round.value should be(1)
      state.decline().toString() should be("End round aborted")
    }
  }
  "When in GameState Exited or EndRoundConfirmation" should {
    val playerVal: PlayerValues =
      PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100),
        minerals = Minerals(100),
        alloys = Alloys(10),
        researchPoints = ResearchPoints(100)
      ), capacity = Capacity(20))

    val stateExit: GameStateManager = GameStateManager(gameState = ExitedState(), playerValues = playerVal)
    val stateEndRound: GameStateManager = GameStateManager(gameState = EndRoundConfirmationState(), playerValues = playerVal)

    "not update the game state and the string representation if move is invoked" in {
      stateExit.move("", Coordinate()).toString should be("Invalid")
      stateEndRound.move("", Coordinate()).toString should be("Invalid")
    }
    "not update the game state and the string representation if save is invoked" in {
      stateExit.save(Option("test")).toString should be("Invalid")
      stateEndRound.save(Option("test")).toString should be("Invalid")
    }
    "not update the game state and the string representation if load is invoked" in {
      stateExit.load(Option("test")).toString should be("Invalid")
      stateEndRound.load(Option("test")).toString should be("Invalid")
    }
    "not update the game state and the string representation if empty is invoked" in {
      stateExit.empty().toString should be("Invalid")
      stateEndRound.empty().toString should be("Invalid")
    }
    "not update the game state and the string representation if exit is invoked" in {
      stateExit.exit().toString should be("Invalid")
      stateEndRound.exit().toString should be("Invalid")
    }
    "not update the game state and the string representation if research is invoked" in {
      stateExit.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials")
        .toString should be("Invalid")
      stateEndRound.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials")
        .toString should be("Invalid")
    }
    "not update the game state and the string representation if build is invoked" in {
      stateExit.build(Hangar(), Hangar().cost, "Constructing: Hangar").toString should be("Invalid")
      stateEndRound.build(Hangar(), Hangar().cost, "Constructing: Hangar").toString should be("Invalid")
    }
    "not update the game state and the string representation if recruit is invoked" in {
      stateExit.recruit(Vector(Corvette()), Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
        .toString should be("Invalid")
      stateEndRound.recruit(Vector(Corvette()), Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
        .toString should be("Invalid")
    }
    "not update the game state and the string representation if show is invoked" in {
      stateExit.show().toString should be("Invalid")
      stateEndRound.show().toString should be("Invalid")
    }
    "not update the game state and the string if an invalid command is invoked" in {
      stateExit.invalid("testst").toString should be("Invalid")
      stateEndRound.invalid("testst").toString should be("Invalid")
    }
    "not update the game state if a message command is invoked" in {
      stateExit.message("something").toString should be("Invalid")
      stateEndRound.message("something").toString should be("Invalid")
    }
    "not update the game state if a sell command is invoked" in {
      stateExit.sell(SellBuildingStrategy(List(Mine()), ResourceHolder(), Capacity()), "sell")
        .toString should be("Invalid")
      stateEndRound.sell(SellBuildingStrategy(List(Mine()), ResourceHolder(), Capacity()), "sell")
        .toString should be("Invalid")
    }
    "update the game state if a accept is invoked" in {
      stateExit.accept().round.value should be(1)
      stateEndRound.accept().toString() should be("Invalid")
    }
    "update the game state if a decline is invoked" in {
      stateExit.decline().round.value should be(1)
      stateEndRound.decline().toString() should be("Invalid")
    }
  }
 }
