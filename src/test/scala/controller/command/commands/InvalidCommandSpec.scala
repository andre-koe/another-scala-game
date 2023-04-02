package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, GameStateStringFormatter, IGameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class InvalidCommandSpec extends AnyWordSpec {
  "The InvalidCommand" should {
    "Set the GameStateManager's GameState to GameState.RUNNING and return the invalid input response" in {
      val gameStateManager: IGameStateManager = new GameStateManager()
      val invalidCommand: InvalidCommand = InvalidCommand("this should be invalid", gameStateManager)
      invalidCommand.execute().gameState should be(GameState.RUNNING)
      invalidCommand.execute()
        .toString should be(GameStateStringFormatter()
        .invalidInputResponse("Unknown Input: 'this should be invalid'"))
    }
  }
}
