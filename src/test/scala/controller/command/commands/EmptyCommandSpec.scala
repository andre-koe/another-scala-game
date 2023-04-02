package controller.command.commands

import model.game.gamestate.{GameStateManager, IGameStateManager, GameState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
class EmptyCommandSpec extends AnyWordSpec {

  "The EmptyCommand" should {
    "'empty' the GameStateManagers toString rep but set the GameState to RUNNING" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val emptyCommand: EmptyCommand = EmptyCommand(gameStateManager)
      emptyCommand.execute().gameState should be(GameState.RUNNING)
      emptyCommand.execute().toString should be("")
    }
  }

}
