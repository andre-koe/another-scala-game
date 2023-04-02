package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class ResearchCommandSpec extends AnyWordSpec {
  "The ResearchCommand" should {
    val gameStateManager: IGameStateManager = GameStateManager()
    "return a new GameState" when {
      val commandEmpty: ResearchCommand = ResearchCommand("", gameStateManager)
      val commandHelp: ResearchCommand = ResearchCommand("help", gameStateManager)
      "initialized with an empty or 'help' input string should " +
        "return GameStateManager with 'research help' string rep" in {

        commandEmpty.execute() should be(commandHelp.execute())
        commandEmpty.execute().gameState should be(GameState.RUNNING)
        commandHelp.execute().gameState should be(GameState.RUNNING)
        commandEmpty.execute()
          .toString should be("research <technology name> - " +
          "Enter list technologies for an overview of all available technologies")
        commandHelp.execute()
          .toString should be("research <technology name> - " +
          "Enter list technologies for an overview of all available technologies")
      }
      "initialized with a valid input string should return a GameStateManager " +
        "with nonempty player.technologyList and string output" in {
        val commandValid: ResearchCommand = ResearchCommand("advanced materials", gameStateManager)
        commandValid.execute().playerValues.listOfTechnologies should not be (empty)
        commandValid.execute().toString should be("Researching: Advanced Materials")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with an invalid input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: ResearchCommand = ResearchCommand("test technology", gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute()
          .toString should be("research: 'test technology' " +
          "- invalid\nEnter help to get an overview of all available commands")
      }
    }
  }
}
