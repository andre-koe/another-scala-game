package controller.command.commands

import model.game.gamestate.GameStateManager
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MoveCommandSpec extends AnyWordSpec {

  "The MoveCommand" should {
    "do absolutely nothing at the Moment except for setting GameState to Running and " +
      "returning an information about missing implementation" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val moveCommand: MoveCommand = MoveCommand("something", gameStateManager)
      moveCommand.execute().toString should be("move not implemented yet")
    }
  }

}
