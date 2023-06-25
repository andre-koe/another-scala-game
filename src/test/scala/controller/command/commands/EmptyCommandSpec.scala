package controller.command.commands

import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.matchers.should.Matchers.*
class EmptyCommandSpec extends AnyWordSpec {

  "The EmptyCommand" should {
    "'empty' the GameStateManagers toString rep but set the GameState to RUNNING" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val emptyCommand: EmptyCommand = EmptyCommand(gameStateManager)
      emptyCommand.execute().toString should be("")
    }
  }

}
