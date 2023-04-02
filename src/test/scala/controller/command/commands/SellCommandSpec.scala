package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}
import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec

class SellCommandSpec extends AnyWordSpec {
  
  "The SellCommand" should {
    "do absolutely nothing at the Moment except for setting GameState to Running and " +
      "returning an information about missing implementation" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val saveCommand: SellCommand = SellCommand("something", gameStateManager)
      saveCommand.execute().gameState should be(GameState.RUNNING)
      saveCommand.execute().toString should be("sell not implemented yet")
    }
  }
}
