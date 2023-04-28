package controller.command.commands

import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class LoadCommandSpec extends AnyWordSpec {
  "The LoadCommand" should {
    "do absolutely nothing at the Moment except for setting GameState to Running and " +
      "returning an information about missing implementation" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val loadCommand: LoadCommand = LoadCommand("", gameStateManager)
      loadCommand.execute().toString should be("load not implemented yet")
    }
  }
}
