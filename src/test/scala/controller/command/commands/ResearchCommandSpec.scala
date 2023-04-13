package controller.command.commands

import model.game.PlayerValues
import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}
import model.purchasable.technology.AdvancedMaterials
import model.resources.ResourceHolder
import model.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.matchers.should.Matchers.*
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
        "with nonempty player listOfTechnologiesCurrentlyResearched and string output" in {
        val plV: PlayerValues = PlayerValues(
          resourceHolder = ResourceHolder(
            researchPoints = ResearchPoints(400)
          ))
        val gsM: IGameStateManager = GameStateManager(playerValues = plV)
        val commandValid: ResearchCommand = ResearchCommand("advanced materials", gsM)
        commandValid.execute().playerValues.listOfTechnologiesCurrentlyResearched should not be (empty)
        commandValid.execute()
          .toString should be("Beginning research of Advanced Materials for Total Cost: " +
          "[Research Points: 200], completion in 3 rounds")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with a valid input string but insufficient funds should return a GameStateManager " +
        "with empty player listOfTechnologiesCurrentlyResearched and string output" in {
        val plV: PlayerValues = PlayerValues(
          resourceHolder = ResourceHolder(
            researchPoints = ResearchPoints(50)
          ))
        val gsM: IGameStateManager = GameStateManager(playerValues = plV)
        val commandValid: ResearchCommand = ResearchCommand("advanced materials", gsM)
        commandValid.execute().playerValues.listOfTechnologiesCurrentlyResearched should be (empty)
        commandValid.execute()
          .toString should be(s"Insufficient Funds --- Total Lacking: [Research Points: 150]")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with a valid input string which is already being researched should return a GameStateManager " +
        "with empty player listOfTechnologiesCurrentlyResearched and string output" in {
        val plV: PlayerValues = PlayerValues(
          resourceHolder = ResourceHolder(
            researchPoints = ResearchPoints(50)
          ),
          listOfTechnologiesCurrentlyResearched = List(AdvancedMaterials())
        )
        val gsM: IGameStateManager = GameStateManager(playerValues = plV)
        val commandValid: ResearchCommand = ResearchCommand("advanced materials", gsM)
        commandValid.execute()
          .toString should be(s"'Advanced Materials' is either being currently researched or has already been researched")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with a valid input string which has already been researched should return a GameStateManager " +
        "with empty player listOfTechnologiesCurrentlyResearched and string output" in {
        val plV: PlayerValues = PlayerValues(
          resourceHolder = ResourceHolder(
            researchPoints = ResearchPoints(50)
          ),
          listOfTechnologies = List(AdvancedMaterials())
        )
        val gsM: IGameStateManager = GameStateManager(playerValues = plV)
        val commandValid: ResearchCommand = ResearchCommand("advanced materials", gsM)
        commandValid.execute().playerValues.listOfTechnologiesCurrentlyResearched should be(empty)
        commandValid.execute()
          .toString should be(s"'Advanced Materials' is either being currently researched or has already been researched")
        commandValid.execute().gameState should be(GameState.RUNNING)
      }
      "initialized with an invalid input string should return an invalid response as GameStateManager.toString" in {
        val commandInvalid: ResearchCommand = ResearchCommand("test technology", gameStateManager)
        commandInvalid.execute().gameState should be(GameState.RUNNING)
        commandInvalid.execute()
          .toString should be("A technology with name 'test technology' does not exist, use " +
          s"'list tech' to get an overview of all available technologies")
      }
    }
  }
}
