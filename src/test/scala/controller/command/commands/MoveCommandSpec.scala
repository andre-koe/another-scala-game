package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, IGameStateManager}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MoveCommandSpec extends AnyWordSpec {

  "The MoveCommand" should {
    "do absolutely nothing at the Moment except for setting GameState to Running and " +
      "returning an information about missing implementation" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val moveCommand: MoveCommand = MoveCommand("something", gameStateManager)
      moveCommand.execute().gameState should be(GameState.RUNNING)
      moveCommand.execute().toString should be("move not implemented yet")
    }
  }

}
