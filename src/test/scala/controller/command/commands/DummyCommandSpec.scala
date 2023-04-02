package controller.command.commands

import model.game.gamestate.{GameStateManager, IGameStateManager, GameState}
import controller.command.commands.DummyCommand
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class DummyCommandSpec extends AnyWordSpec {

  "The DummyCommand" should {
    "do absolutely nothing to the GameStateManager except returning its current gameState" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val dummyCommand: DummyCommand = DummyCommand(gameStateManager)
      dummyCommand.execute().gameState should be(GameState.INIT)
      dummyCommand.execute().toString should be(gameStateManager.toString)
    }
  }

}
