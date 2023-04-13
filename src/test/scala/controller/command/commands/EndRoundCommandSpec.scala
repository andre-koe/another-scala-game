package controller.command.commands

import model.game.gamestate.{GameState, GameStateManager, GameStateStringFormatter, IGameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class EndRoundCommandSpec extends AnyWordSpec {

  "The EndRoundCommand" should {
    "Set the GameStateManager's GameState to GameState.END_ROUND_REQUEST and ask for confirmation" in {
      val gameStateManager: IGameStateManager = new GameStateManager()
      val endRoundCommand: EndRoundCommand = EndRoundCommand(gameStateManager)
      endRoundCommand.execute().gameState should be(GameState.END_ROUND_REQUEST)
      endRoundCommand.execute().toString should be("Are you sure? [yes (y) / no (n)]")
    }
  }

}
