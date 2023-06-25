package controller.command.commands

import model.game.gamestate.GameStateManager
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import utils.DefaultValueProvider.given_IGameValues
import utils.DefaultValueProvider.given_IFileIOStrategy
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import better.files.*
import model.core.board.GameBoardBuilder
import model.core.board.boardutils.Coordinate
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.fileIO.{JSONStrategy, XMLStrategy}
import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer}
import model.core.gameobjects.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer}
import model.core.mechanics.fleets.Fleet
import model.game.playervalues.PlayerValues

import java.io.File
import java.nio.file.{Files, Path}

class SaveCommandSpec extends AnyWordSpec with BeforeAndAfterEach with BeforeAndAfterAll {

  var tempDir: Path = _

  override def beforeAll(): Unit =
    tempDir = Files.createTempDirectory("savegamesSaveCommandSpec")
  override def afterAll(): Unit = clean()


  "The SaveCommand" should {
    "Create a folder savegames if it doesnt exist yet" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(Some("just-once"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.exists shouldBe(true)
    }
    "Store file with name (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(Some("testJ"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.listFiles().exists(_.getName == "testJ.json") shouldBe (true)
    }
    "Store file with name (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(Some("testX"), gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.listFiles().exists(_.getName == "testX.xml") shouldBe (true)
    }
    "Store file without name (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(None, gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.listFiles().count(_.getName.contains(".json")) shouldBe (3)
    }
    "Store file without name (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(None, gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.listFiles().count(_.getName.contains(".xml")) shouldBe (2)
    }
    "Store gsm with fleets, sectors and buildings (XML)" in {
      val sector = Sector(location = Coordinate(0,0),
        affiliation = Affiliation.PLAYER,
        sectorType = SectorType.BASE,
        unitsInSector = Vector(
          Fleet(name = "test",
            fleetComponents = Vector(Corvette(), Battleship()),
            location = Coordinate(0,0),
            affiliation = Affiliation.PLAYER),
          Fleet(name = "test2",
            fleetComponents = Vector(Cruiser(), Destroyer()),
            location = Coordinate(0,0),
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

      val gsm: GameStateManager =
        GameStateManager(gameMap = GameBoardBuilder().build.updateSector(playerSector), playerValues = pValues)
      val saveCommand: SaveCommand = SaveCommand(Some("a_complex_state"), gsm)(using fileIOStrategy = XMLStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.listFiles().exists(_.getName == ("a_complex_state.xml")) shouldBe (true)
    }
    "Store gsm with fleets, sectors and buildings (JSON)" in {
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

      val gsm: GameStateManager =
        GameStateManager(gameMap = GameBoardBuilder().build.updateSector(playerSector), playerValues = pValues)
      val saveCommand: SaveCommand = SaveCommand(Some("a_complex_state"), gsm)(using fileIOStrategy = JSONStrategy(tempDir.toFile))
      saveCommand.execute()
      tempDir.toFile.listFiles().exists(_.getName == ("a_complex_state.json")) shouldBe (true)
    }
  }

  def clean(): Unit =
    val directory = tempDir.toFile
    directory.listFiles().foreach(_.delete())
    directory.delete()

}
