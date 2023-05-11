package controller.command.commands

import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class EndRoundCommandSpec extends AnyWordSpec {

  "The EndRoundCommand" should {
    "Set the GameStateManager's GameState to GameState.END_ROUND_REQUEST and ask for confirmation" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val endRoundCommand: EndRoundCommand = EndRoundCommand(gameStateManager)
      endRoundCommand.execute().toString should be("Are you sure? [yes (y) / no (n)]")
    }
  }

}
