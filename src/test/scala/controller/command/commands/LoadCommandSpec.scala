package controller.command.commands

import model.core.fileIO.{JSONStrategy, XMLStrategy}
import model.core.utilities.Round
import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import utils.DefaultValueProvider.given_IFileIOStrategy
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.matchers.should.Matchers.*
import better.files.*
import better.files.File.*
import better.files.Dsl.*
import org.scalatest.BeforeAndAfterAll
import java.io.File

class LoadCommandSpec extends AnyWordSpec with BeforeAndAfterAll {

  val dir: java.io.File = java.io.File("./savegamesLoadCommandSpec")

  override def beforeAll(): Unit =
    val tbsGSM: GameStateManager = GameStateManager(round = Round(10))
    val saveCommandJ: SaveCommand = SaveCommand(Some("TestJ"), tbsGSM)(using fileIOStrategy = JSONStrategy(dir))
    val saveCommandX: SaveCommand = SaveCommand(Some("TestX"), tbsGSM)(using fileIOStrategy = XMLStrategy(dir))
    saveCommandJ.execute()
    saveCommandX.execute()

  override def afterAll(): Unit = dir.delete()


  "The LoadCommand" should {

    "return the current state if state GameStateManager (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("doesnt-exist.json"), gsm)(using fileIOStrategy = JSONStrategy(dir))
      loadCommand.execute().round.value should be(1)
    }

    "return a previously saved GameStateManager (JSON)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("TestJ.json"), gsm)(using fileIOStrategy = JSONStrategy(dir))
      loadCommand.execute().round.value should be(10)
    }

    "return the current state if state GameStateManager (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("doesnt-exist.xml"), gsm)(using fileIOStrategy = XMLStrategy(dir))
      loadCommand.execute().round.value should be(1)
    }

    "return a previously saved GameStateManager (XML)" in {
      val gsm: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand(Some("TestX.xml"), gsm)(using fileIOStrategy = XMLStrategy(dir))
      loadCommand.execute().round.value should be(10)
    }

  }

}
