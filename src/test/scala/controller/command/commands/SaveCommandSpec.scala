package controller.command.commands

import model.game.gamestate.{GameStateManager, IGameStateManager, GameState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
class SaveCommandSpec extends AnyWordSpec {
  "The SaveCommand" should {
    "do absolutely nothing at the Moment except for setting GameState to Running and " +
      "returning an information about missing implementation" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val saveCommand: SaveCommand = SaveCommand("", gameStateManager)
      saveCommand.execute().gameState should be(GameState.RUNNING)
      saveCommand.execute().toString should be("save not implemented yet")
    }
  }
}