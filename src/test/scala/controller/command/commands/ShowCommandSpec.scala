package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, GameStateStringFormatter, IGameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShowCommandSpec extends AnyWordSpec {

  "The ShowCommand" should {
    "should set the GameState to running and display the overview" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val showCommand: ShowCommand = ShowCommand("overview", gameStateManager)
      showCommand.execute().gameState should be(GameState.RUNNING)
      showCommand.execute().toString should be(
        GameStateStringFormatter(playerValues = gameStateManager.playerValues,
          gameStateManager = gameStateManager).overview())
    }
    "should set the GameState to running and display an explanatory String" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val showEmptyCommand: ShowCommand = ShowCommand("", gameStateManager)
      val showHelpCommand: ShowCommand = ShowCommand("help", gameStateManager)
      showEmptyCommand.execute().gameState should be(GameState.RUNNING)
      showEmptyCommand.execute().toString should be(showHelpCommand.execute().toString)
      showEmptyCommand.execute()
        .toString should be("show overview - display the current player stats buildings, tech, units, income, etc.")
    }
    "should set the GameState to running and display an invalid response" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val showSomethingCommand: ShowCommand = ShowCommand("invalid", gameStateManager)
      showSomethingCommand.execute().gameState should be(GameState.RUNNING)
      showSomethingCommand.execute().toString should be("show: 'invalid' " +
        "- invalid\nEnter help to get an overview of all available commands")
    }
  }

}
