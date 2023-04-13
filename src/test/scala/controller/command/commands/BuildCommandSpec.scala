package controller.command.commands

import model.game.PlayerValues
import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

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
          "Enter list buildings for an overview of all available buildings.")
        commandHelp.execute()
          .toString should be ("build <building name> - " +
          "Enter list buildings for an overview of all available buildings.")
      }
      "initialized with a valid input string and sufficient funds should return a GameStateManager " +
      "with nonempty player.listOfBuildingsUnderConstruction and string output notfying the user" in {
        val playerValues: PlayerValues = PlayerValues(resourceHolder = ResourceHolder(energy = Energy(1000),
          minerals = Minerals(1000),
          alloys = Alloys(1000),
          researchPoints = ResearchPoints(1000)))
        val gameStateManager: IGameStateManager = GameStateManager(playerValues = playerValues)
        val commandValid: BuildCommand = BuildCommand("Research Lab", gameStateManager)
        commandValid.execute().playerValues.listOfBuildingsUnderConstruction should not be(empty)
        commandValid.execute().toString should be(s"Beginning construction of Research Lab for " +
          s"Total Cost: [Energy: 100] [Minerals: 100] [Alloys: 100], completion in 3 rounds.")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with a valid input string but insufficient funds should return a GameStateManager " +
        "with empty player.listOfBuildingsUnderConstruction and string output notifying the user" in {
        val commandValid: BuildCommand = BuildCommand("Research Lab", gameStateManager)
        commandValid.execute().playerValues.listOfBuildingsUnderConstruction should be(empty)
        commandValid.execute().toString should be("Insufficient Funds --- Total Lacking: [Alloys: 90].")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with an invalid input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: BuildCommand = BuildCommand("Testhouse", gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute().toString should be("A building with name 'Testhouse' does not exist, use " +
          "'list building' to get an overview of all available buildings.")
      }
    }
  }

}
