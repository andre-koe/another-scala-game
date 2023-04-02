package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, GameStateStringFormatter, IGameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ListCommandSpec extends AnyWordSpec {

  "The ListCommand" should {
    "return a new toString representation depending on the input" when {
      "asked for list building(s) should list all Buildings" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listBuildingsCommand: ListCommand = ListCommand("building", gameStateManager)
        val listBuildingCommand: ListCommand = ListCommand("building", gameStateManager)

        listBuildingsCommand.execute().gameState should be(GameState.RUNNING)
        listBuildingCommand.execute().gameState should be(GameState.RUNNING)
        listBuildingsCommand.execute().toString should be(listBuildingCommand.execute().toString)
        listBuildingsCommand.execute().toString should be(GameStateStringFormatter().listBuildings)
      }
      "asked for list technology/tech/technologies should list all Technologies" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listTech: ListCommand = ListCommand("tech", gameStateManager)
        val listTechnology: ListCommand = ListCommand("technology", gameStateManager)
        val listTechnologies: ListCommand = ListCommand("technologies", gameStateManager)

        listTech.execute().gameState should be(GameState.RUNNING)
        listTechnology.execute().gameState should be(GameState.RUNNING)
        listTechnologies.execute().gameState should be(GameState.RUNNING)

        listTech.execute().toString should be(listTechnology.execute().toString)
        listTech.execute().toString should be(listTechnologies.execute().toString)
        listTech.execute().toString should be(GameStateStringFormatter().listTechnologies)
      }
      "asked for list unit(s) should list all Units" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listUnits: ListCommand = ListCommand("units", gameStateManager)
        val listUnit: ListCommand = ListCommand("unit", gameStateManager)

        listUnits.execute().gameState should be(GameState.RUNNING)
        listUnit.execute().gameState should be(GameState.RUNNING)
        listUnits.execute().toString should be(listUnit.execute().toString)
        listUnits.execute().toString should be(GameStateStringFormatter().listUnits)
      }
      "asked for list help should return an explanatory string as gamestate.tostring" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listHelp: ListCommand = ListCommand("help", gameStateManager)

        listHelp.execute().gameState should be(GameState.RUNNING)
        listHelp.execute()
          .toString should be("The list command with optional parameter (units/buildings/technologies) " +
          "will list all available Game Objects according to input, if omitted everything will be listed." +
          "\nEnter list help to see this message")
      }
      "asked for list with empty input should list everything" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listAll: ListCommand = ListCommand("", gameStateManager)

        listAll.execute().gameState should be(GameState.RUNNING)
        listAll.execute().toString should be(GameStateStringFormatter().listAll)
      }
      "asked for list invalid should return an invalid input response as GameState.toString" in {
        val gameStateManager: IGameStateManager = GameStateManager()
        val listInvalid: ListCommand = ListCommand("test", gameStateManager)

        listInvalid.execute().gameState should be(GameState.RUNNING)
        listInvalid.execute().toString should be(GameStateStringFormatter().invalidInputResponse("list: 'test'"))
      }
    }
  }
}
