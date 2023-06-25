package model.game.gamestate

import model.core.board.sector.ISector
import utils.DefaultValueProvider.given_IGameValues
import utils.DefaultValueProvider.given_IFileIOStrategy
import model.core.board.sector.impl.{IPlayerSector, PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.fileIO.{JSONStrategy, XMLStrategy}
import model.core.gameobjects.purchasable.building.{Hangar, IBuilding, Mine, ResearchLab}
import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, ITechnology, Polymer}
import model.core.gameobjects.purchasable.units.Corvette
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ResourceHolder, Round}
import model.game.*
import model.game.gamestate.GameStateStringFormatter
import model.game.gamestate.gamestates.{EndRoundConfirmationState, ExitedState, RunningState, WaitForUserConfirmation}
import model.game.gamestate.strategies.sell.SellBuildingStrategy
import model.game.playervalues.{IPlayerValues, PlayerValues}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import java.io
import java.io.File
import better.files.*
import controller.command.commands.SaveCommand

import java.nio.file.{Files, Path}


class GameStateManagerSpec extends AnyWordSpec with BeforeAndAfterAll {

  var tempDir: Path = _

  override def beforeAll(): Unit =
    tempDir = Files.createTempDirectory("saveGamesGSMTest")

  override def afterAll(): Unit =
    val directory = tempDir.toFile
    directory.listFiles().foreach(_.delete())
    directory.delete()


  val aSector: ISector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.REGULAR)

  "The GameStateManager" when {
    "in state Running (Initialized)" should {
      val playerVal: PlayerValues =
        playervalues.PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100),
          minerals = Minerals(100),
          alloys = Alloys(10),
          researchPoints = ResearchPoints(100)
        ), capacity = Capacity(20))
      val state: IGameStateManager = GameStateManager(playerValues = Vector(playerVal))
      "have a Round value of 1" in {
        state.round.value should be(1)
      }
      "have an Energy value of 100" in {
        state.currentPlayerValues.resourceHolder.energy.value should be(100)
      }
      "have a Mineral value of 100" in {
        state.currentPlayerValues.resourceHolder.minerals.value should be(100)
      }
      "have an Alloy value of 10" in {
        state.currentPlayerValues.resourceHolder.alloys.value should be(10)
      }
      "have a Research value of 100" in {
        state.currentPlayerValues.resourceHolder.researchPoints.value should be(100)
      }
      "have a list of technologies" in {
        val gameStateManager = GameStateManager(playerValues = Vector(PlayerValues(listOfTechnologies = Vector[ITechnology](Polymer()))))
        gameStateManager.currentPlayerValues.listOfTechnologies shouldBe a[Vector[_]]
        gameStateManager.currentPlayerValues.listOfTechnologies should not be empty
        gameStateManager.currentPlayerValues.listOfTechnologies.head shouldBe a[ITechnology]
      }
    }
    "handling a player command" should {
      val state: IGameStateManager = GameStateManager()

      "update the game state and the string representation if move is invoked" in {
        state.move("",Coordinate(0,0)).toString should be("The specified fleet or sector doesn't exist")
      }
      "update the game state and the string representation if save is invoked" in {
        state.save(JSONStrategy(tempDir.toFile), Option("Test")).toString should be("")
        checkFile("Test", "json") shouldBe true
      }
      "update the game state and the string representation if load is invoked" in {
        state.save(JSONStrategy(tempDir.toFile), Option("Test"))
        state.load(JSONStrategy(tempDir.toFile), Option("Test.json")).toString should be("")
      }
      "update the game state and the string representation if empty is invoked" in {
        state.empty().toString should be("")
      }
      "update the game state and the string representation if exit is invoked" in {
        state.exit().toString should be (GameStateStringFormatter(gsm = state).goodbyeResponse)
      }
      "update the game state and the string representation if research is invoked" in {
        state.research(AdvancedMaterials(), AdvancedMaterials().cost, "Researching: Advanced Materials").toString should be("Researching: Advanced Materials")
      }
      "update the game state and the string representation if build is invoked" in {
        val sector: IPlayerSector = PlayerSector(aSector, constQuBuilding = Vector(Hangar()))
        state.build(sector, Hangar().cost, "Constructing: Hangar").toString should be("Constructing: Hangar")
      }
      "update the game state and the string representation if recruit is invoked" in {
        val sector: IPlayerSector = PlayerSector(aSector, constQuUnits = Vector(Corvette()))
        state.recruit(sector, Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
          .toString should be("Recruiting: 1 x Corvette")
      }
      "update the game state and the string representation if show is invoked" in {
        state.show()
          .toString should be(GameStateStringFormatter(gsm = state).overview())
      }
      "update the game state and the string if an invalid command is invoked" in {
        state.invalid("testst")
          .toString should be(GameStateStringFormatter(gsm = state).invalidInputResponse("testst"))
      }
    }
    "handling the [end round] mechanic" should {
      val tmpGameState: IGameStateManager = GameStateManager().endRoundRequest()
      "prompt the user for input and wait if end round action was triggered" in {
        tmpGameState.toString should be ("Are you sure? [yes (y) / no (n)]")
      }
      "end the round if the user accepts after the prompt" in {
        var endRoundGameState: IGameStateManager = tmpGameState
        endRoundGameState = endRoundGameState.accept()
        endRoundGameState.round.value should be(2)
      }
    }
  }
  "When in GameState WaitForUserConfirmation" should {
    val playerVal = PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100),
        minerals = Minerals(100),
        alloys = Alloys(10),
        researchPoints = ResearchPoints(100)
      ), capacity = Capacity(20))

    val state: IGameStateManager = GameStateManager(playerValues = Vector(playerVal), gameState = WaitForUserConfirmation())

    "not update the game state and the string representation if move is invoked" in {
      state.move("", Coordinate(0,0)).toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if save is invoked" in {
      state.save(JSONStrategy(), Option("test")).toString should be("Invalid")
    }
    "not update the game state and the string representation if load is invoked" in {
      state.load(JSONStrategy(), Option("test")).toString should be("Invalid")
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
      val sector: IPlayerSector = PlayerSector(aSector, constQuBuilding = Vector(Hangar()))
      state.build(sector, Hangar().cost, "Constructing: Hangar")
        .toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "not update the game state and the string representation if recruit is invoked" in {
      val sector: IPlayerSector = PlayerSector(aSector, constQuBuilding = Vector(Hangar()))
      state.recruit(sector, Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
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
      state.sell(SellBuildingStrategy(
        PlayerSector(Sector(location = Coordinate(0,0)), buildingsInSector = Vector(Mine())), Mine(), 1))
        .toString should be("Are you sure? [yes (y) / no (n)]")
    }
    "update the game state if a accept is invoked" in {
      state.accept().round.value should be(2)
      state.accept().toString should be(GameStateStringFormatter(round = Round(), gsm = state.accept())
        .overview(state.round.next, state.currentPlayerValues.resourceHolder))
    }
    "update the game state if a decline is invoked" in {
      state.decline().round.value should be(1)
      state.decline().toString should be("End round aborted")
    }
  }
  "When in GameState Exited or EndRoundConfirmation" should {
    val playerVal: IPlayerValues =
      playervalues.PlayerValues(resourceHolder = ResourceHolder(energy = Energy(100),
        minerals = Minerals(100),
        alloys = Alloys(10),
        researchPoints = ResearchPoints(100)
      ), capacity = Capacity(20))

    val stateExit: IGameStateManager = GameStateManager(playerValues = Vector(playerVal), gameState = ExitedState())
    val stateEndRound: IGameStateManager = GameStateManager(playerValues = Vector(playerVal), gameState = EndRoundConfirmationState())

    "not update the game state and the string representation if move is invoked" in {
      stateExit.move("", Coordinate(0,0)).toString should be("Invalid")
      stateEndRound.move("", Coordinate(0,0)).toString should be("Invalid")
    }
    "not update the game state and the string representation if save is invoked" in {
      stateExit.save(JSONStrategy(), Option("test")).toString should be("Invalid")
      stateEndRound.save(JSONStrategy(), Option("test")).toString should be("Invalid")
    }
    "not update the game state and the string representation if load is invoked" in {
      stateExit.load(JSONStrategy(), Option("test")).toString should be("Invalid")
      stateEndRound.load(JSONStrategy(), Option("test")).toString should be("Invalid")
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
      val sector: IPlayerSector = PlayerSector(aSector, constQuBuilding = Vector(Hangar()))
      stateExit.build(sector, Hangar().cost, "Constructing: Hangar").toString should be("Invalid")
      stateEndRound.build(sector, Hangar().cost, "Constructing: Hangar").toString should be("Invalid")
    }
    "not update the game state and the string representation if recruit is invoked" in {
      val sector: IPlayerSector = PlayerSector(aSector, constQuUnits = Vector(Corvette()))
      stateExit.recruit(sector, Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
        .toString should be("Invalid")
      stateEndRound.recruit(sector, Corvette().cost, Corvette().capacity, "Recruiting: 1 x Corvette")
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
    "update the game state if a accept is invoked" in {
      stateExit.accept().round.value should be(1)
      stateEndRound.accept().toString should be("Invalid")
    }
    "update the game state if a decline is invoked" in {
      stateExit.decline().round.value should be(1)
      stateEndRound.decline().toString should be("Invalid")
    }
  }

  def checkFile(f: String, ext: String): Boolean =
    tempDir.toFile.listFiles().exists(_.getName == f + "." + ext)

}
