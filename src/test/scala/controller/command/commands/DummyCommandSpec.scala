package controller.command.commands

import model.game.gamestate.GameStateManager
import controller.command.commands.DummyCommand
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class DummyCommandSpec extends AnyWordSpec {

  "The DummyCommand" should {
    "do absolutely nothing to the GameStateManager except returning its current gameState" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val dummyCommand: DummyCommand = DummyCommand(gameStateManager)
      dummyCommand.execute().toString should be(gameStateManager.toString)
    }
  }

}
