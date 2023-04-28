package controller.command.commands

import model.game.gamestate.{GameStateStringFormatter, GameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShowCommandSpec extends AnyWordSpec {

  "The ShowCommand" should {
    "should set the GameState to running and display the overview" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val showCommand: ShowCommand = ShowCommand("overview", gameStateManager)
      showCommand.execute().toString should be(
        GameStateStringFormatter(playerValues = gameStateManager.playerValues,
          gameStateManager = gameStateManager).overview())
    }
    "should set the GameState to running and display an explanatory String" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val showEmptyCommand: ShowCommand = ShowCommand("", gameStateManager)
      val showHelpCommand: ShowCommand = ShowCommand("help", gameStateManager)
      showEmptyCommand.execute().toString should be(showHelpCommand.execute().toString)
      showEmptyCommand.execute()
        .toString should be("show overview - display the current player stats buildings, tech, units, income, etc.")
    }
    "should set the GameState to running and display an invalid response" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val showSomethingCommand: ShowCommand = ShowCommand("invalid", gameStateManager)
      showSomethingCommand.execute().toString should be("show: 'invalid' " +
        "- invalid\nEnter help to get an overview of all available commands")
    }
  }

}
