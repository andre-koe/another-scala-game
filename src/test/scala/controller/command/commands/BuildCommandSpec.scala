package controller.command.commands

import model.game.gamestate.{GameStateManager, IGameStateManager, GameState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BuildCommandSpec extends AnyWordSpec {

  "The BuildCommand" should {
    val gameStateManager: IGameStateManager = GameStateManager()
    "return a new GameState" when {
      val commandEmpty: BuildCommand = BuildCommand("", gameStateManager)
      val commandHelp: BuildCommand = BuildCommand("help", gameStateManager)
      "initialized with an empty or 'help' input string should " +
        "return GameStateManager with 'build help' string rep" in {

        commandEmpty.execute() should be (commandHelp.execute())
        commandEmpty.execute().gameState should be (GameState.RUNNING)
        commandHelp.execute().gameState should be (GameState.RUNNING)
        commandEmpty.execute()
          .toString should be ("build <building name> - " +
          "Enter list buildings for an overview of all available buildings")
        commandHelp.execute()
          .toString should be ("build <building name> - " +
          "Enter list buildings for an overview of all available buildings")
      }
      "initialized with a valid input string should return a GameStateManager " +
        "with nonempty player.buildlist and string output" in {
        val commandValid: BuildCommand = BuildCommand("Research Lab", gameStateManager)
        commandValid.execute().playerValues.listOfBuildings should not be(empty)
        commandValid.execute().toString should be("Constructing: Research Lab")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with an invalid input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: BuildCommand = BuildCommand("Testhouse", gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute()
          .toString should be("build Testhouse - invalid\nEnter help to get an overview of all available commands")
      }
    }
  }

}
