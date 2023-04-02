package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class RecruitCommandSpec extends AnyWordSpec {
  "The RecruitCommand" should {
    val gameStateManager: IGameStateManager = GameStateManager()
    "return a new GameState" when {
      val commandEmpty: RecruitCommand = RecruitCommand("", gameStateManager)
      val commandHelp: RecruitCommand = RecruitCommand("help", gameStateManager)
      "initialized with an empty or 'help' input string should " +
        "return GameStateManager with 'recruit help' string rep" in {

        commandEmpty.execute() should be(commandHelp.execute())
        commandEmpty.execute().gameState should be(GameState.RUNNING)
        commandHelp.execute().gameState should be(GameState.RUNNING)
        commandEmpty.execute()
          .toString should be("recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
          " - Enter list units for an overview of all available units")
        commandHelp.execute()
          .toString should be("recruit <unit name> (quantity) if quantity is omitted default 1 will be used" +
          " - Enter list units for an overview of all available units")
      }
      "initialized with a valid input string without quantity should return a GameStateManager " +
        "with nonempty player.unitList and string output" in {
        val commandValid: RecruitCommand = RecruitCommand("Cruiser", gameStateManager)
        commandValid.execute().playerValues.listOfUnits should not be (empty)
        commandValid.execute().toString should be("Recruiting: 1 x Cruiser")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with a valid input string with quantity should return a GameStateManager " +
        "with nonempty player.unitList and string output" in {
        val commandValid: RecruitCommand = RecruitCommand("Cruiser 3", gameStateManager)
        commandValid.execute().playerValues.listOfUnits should not be (empty)
        commandValid.execute().toString should be("Recruiting: 3 x Cruiser")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with a valid unit but invalid quantity" +
        " input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: RecruitCommand = RecruitCommand("Cruiser drei", gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute()
          .toString should be("recruit: 'Cruiser drei' - invalid\nEnter help to get an overview of all available commands")
      }
      "initialized with an invalid input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: RecruitCommand = RecruitCommand("Testunit", gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute()
          .toString should be("recruit: 'Testunit' - invalid\nEnter help to get an overview of all available commands")
      }
      "initialized with an invalid input and too many parameters should " +
        "return an invalid response as GameStateManager.toString" in {
        val commandInvalid: RecruitCommand = RecruitCommand(null, gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute()
          .toString should be("recruit: 'null' - " +
          "invalid\nEnter help to get an overview of all available commands")
      }

    }
  }
}
