package controller.command.commands

import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class InvalidCommandSpec extends AnyWordSpec {
  "The InvalidCommand" should {
    "Set the GameStateManager's GameState to GameState.RUNNING and return the invalid input response" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val invalidCommand: InvalidCommand = InvalidCommand("this should be invalid", gameStateManager)
      invalidCommand.execute()
        .toString should be(GameStateStringFormatter(gameStateManager = gameStateManager)
        .invalidInputResponse("Unknown Input: 'this should be invalid'"))
    }
  }
}
