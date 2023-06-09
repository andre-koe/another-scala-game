package controller.command.commands

import model.game.gamestate.{GameStateStringFormatter, GameStateManager}
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShowCommandSpec extends AnyWordSpec {

  "The ShowCommand" should {
    "should set the GameState to running and display the overview" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val showCommand: ShowCommand = ShowCommand(gameStateManager)
      showCommand.execute().toString should be(
        GameStateStringFormatter(gsm = gameStateManager).overview())
    }
  }

}
