package controller.command.commands

import model.game.gamestate.GameStateManager
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach}
import utils.DefaultValueProvider.given_IGameValues
import utils.DefaultValueProvider.given_IFileIOStrategy
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import better.files.*
import model.core.fileIO.{JSONStrategy, XMLStrategy}

import java.io.File

class SaveCommandSpec extends AnyWordSpec with BeforeAndAfterEach with BeforeAndAfterAll {

  val dir: java.io.File = java.io.File("./savegamesSaveCommandSpec")
  override def beforeEach(): Unit = if dir.exists then dir.delete()

  override def afterAll(): Unit = if dir.exists then dir.delete()
  
  "The SaveCommand" should {
    "Create a folder savegames if it doesnt exist yet" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(Some("just-once"), gsm)(using fileIOStrategy = JSONStrategy(dir))
      saveCommand.execute()
      "./savegamesSaveCommandSpec".toFile.exists shouldBe(true)
    }
    "Store file with name (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(Some("testJ"), gsm)(using fileIOStrategy = JSONStrategy(dir))
      saveCommand.execute()
      ("./savegamesSaveCommandSpec".toFile / "testJ.json").exists shouldBe (true)
    }
    "Store file with name (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(Some("testX"), gsm)(using fileIOStrategy = XMLStrategy(dir))
      saveCommand.execute()
      ("./savegamesSaveCommandSpec".toFile / "testX.xml").exists shouldBe (true)
    }
    "Store file without name (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(None, gsm)(using fileIOStrategy = JSONStrategy(dir))
      saveCommand.execute()
      "./savegamesSaveCommandSpec".toFile.children.nonEmpty shouldBe (true)
    }
    "Store file without name (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand(None, gsm)(using fileIOStrategy = XMLStrategy(dir))
      saveCommand.execute()
      "./savegamesSaveCommandSpec".toFile.children.nonEmpty shouldBe (true)
    }
  }
}
