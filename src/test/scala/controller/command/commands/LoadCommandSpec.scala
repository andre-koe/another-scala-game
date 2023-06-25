package controller.command.commands

import model.core.fileIO.{JSONStrategy, XMLStrategy}
import model.core.utilities.Round
import model.game.gamestate.{GameStateManager, IGameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import utils.DefaultValueProvider.given_IFileIOStrategy
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.matchers.should.Matchers.*
import better.files.*
import better.files.File.*
import better.files.Dsl.*
import model.core.board.GameBoardBuilder
import model.core.board.boardutils.Coordinate
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer}
import model.core.gameobjects.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer}
import model.core.mechanics.fleets.Fleet
import model.game.playervalues.PlayerValues
import org.scalatest.BeforeAndAfterAll

import java.io.File
import java.nio.file.{Files, Path}

class LoadCommandSpec extends AnyWordSpec with BeforeAndAfterAll {

  var tempDir: Path = _

  override def beforeAll(): Unit =
    tempDir = Files.createTempDirectory("savegamesLoadCommandSpec")

    val tbsGSM: GameStateManager = GameStateManager(round = Round(10))
    val saveCommandJ: SaveCommand = SaveCommand(Some("TestJ"), tbsGSM)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
    val saveCommandX: SaveCommand = SaveCommand(Some("TestX"), tbsGSM)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
    saveCommandJ.execute()
    saveCommandX.execute()

  override def afterAll(): Unit =
    val directory = tempDir.toFile
    directory.listFiles().foreach(_.delete())
    directory.delete()


  "The LoadCommand" should {

    "return the current state if state GameStateManager (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("doesnt-exist.json"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(1)
    }

    "return a previously saved GameStateManager (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("TestJ.json"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(10)
    }

    "return the current state if state GameStateManager (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("doesnt-exist.xml"), gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(1)
    }

    "return a previously saved GameStateManager (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("TestX.xml"), gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(10)
    }

    "return the latest Savegame if no name is specified (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(None, gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(10)
    }

    "return the latest Savegame if no name is specified (JSON)" in {
      SaveCommand(Some("TestJ"), GameStateManager(round = Round(10)))(using fileIOStrategy = JSONStrategy(tempDir.toFile)).execute()
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(None, gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(10)
    }

    "return a complex gamestate savegame correctly (XML)" in {
      val gsm = getFullyFledgedGSM
      val saveCommand: SaveCommand = SaveCommand(Some("a_complex_state"), gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      saveCommand.execute()
      val loadCommand: LoadCommand = LoadCommand(Some("a_complex_state"), gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(1)

      loadCommand.execute().gameMap.data(0)(0).unitsInSector.size should be(2)
      loadCommand.execute()
        .gameMap.data(0)(0)
        .unitsInSector.flatMap(_.fleetComponents) should be(Vector(Corvette(), Battleship(), Cruiser(), Destroyer()))
      loadCommand.execute()
        .gameMap.data(0)(0)
        .affiliation should be(Affiliation.PLAYER)
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.location should be(Coordinate(0, 0))
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.buildingsInSector should be(Vector(Shipyard(), Hangar(), ResearchLab()))
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.constQuBuilding should be(Vector(EnergyGrid(), Mine(), Factory()))
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.constQuUnits should be(Vector(Corvette()))
      loadCommand.execute()
        .playerValues
        .length should be(2)
      loadCommand.execute()
        .currentPlayerValues
        .listOfTechnologies should be(Vector(NanoRobotics()))
      loadCommand.execute()
        .currentPlayerValues
        .listOfTechnologiesCurrentlyResearched should be(Vector(Polymer(), AdvancedMaterials(), AdvancedPropulsion()))
    }

    "return a complex gamestate savegame correctly (JSON)" in {

      val gsm = getFullyFledgedGSM

      val saveCommand: SaveCommand = SaveCommand(Some("a_complex_state"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      saveCommand.execute()
      val loadCommand: LoadCommand = LoadCommand(Some("a_complex_state"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      loadCommand.execute().round.value should be(1)

      loadCommand.execute().gameMap.data(0)(0).unitsInSector.size should be(2)
      loadCommand.execute()
        .gameMap.data(0)(0)
        .unitsInSector.flatMap(_.fleetComponents) should be(Vector(Corvette(), Battleship(), Cruiser(), Destroyer()))
      loadCommand.execute()
        .gameMap.data(0)(0)
        .affiliation should be(Affiliation.PLAYER)
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.location should be(Coordinate(0, 0))
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.buildingsInSector should be(Vector(Shipyard(), Hangar(), ResearchLab()))
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.constQuBuilding should be(Vector(EnergyGrid(), Mine(), Factory()))
      loadCommand.execute()
        .gameMap
        .getPlayerSectors(Affiliation.PLAYER)
        .head.constQuUnits should be(Vector(Corvette()))
      loadCommand.execute()
        .playerValues
        .length should be(2)
      loadCommand.execute()
        .currentPlayerValues
        .listOfTechnologies should be(Vector(NanoRobotics()))
      loadCommand.execute()
        .currentPlayerValues
        .listOfTechnologiesCurrentlyResearched should be(Vector(Polymer(), AdvancedMaterials(), AdvancedPropulsion()))
    }

  }

  def getFullyFledgedGSM: IGameStateManager =
    val sector = Sector(location = Coordinate(0, 0),
      affiliation = Affiliation.PLAYER,
      sectorType = SectorType.BASE,
      unitsInSector = Vector(
        Fleet(name = "test",
          fleetComponents = Vector(Corvette(), Battleship()),
          location = Coordinate(0, 0),
          affiliation = Affiliation.PLAYER),
        Fleet(name = "test2",
          fleetComponents = Vector(Cruiser(), Destroyer()),
          location = Coordinate(0, 0),
          affiliation = Affiliation.PLAYER)
      ))
    val playerSector =
      PlayerSector(sector = sector,
        constQuBuilding = Vector(EnergyGrid(), Mine(), Factory()),
        constQuUnits = Vector(Corvette()), buildingsInSector = Vector(Shipyard(), Hangar(), ResearchLab()))

    val pValues = Vector(
      PlayerValues(
        affiliation = Affiliation.PLAYER,
        listOfTechnologiesCurrentlyResearched = Vector(Polymer(), AdvancedMaterials(), AdvancedPropulsion()),
        listOfTechnologies = Vector(NanoRobotics()),
      ), PlayerValues())
    GameStateManager(gameMap = GameBoardBuilder().build.updateSector(playerSector), playerValues = pValues)

}
