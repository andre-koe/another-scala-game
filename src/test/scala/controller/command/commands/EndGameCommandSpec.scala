package controller.command.commands

import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class EndGameCommandSpec extends AnyWordSpec {

  "The EndGameCommand" should {
    "set the GameStateManagers gameState to EXIT and let him say goodbye" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val endGameCommand: EndGameCommand = EndGameCommand(gameStateManager)
      endGameCommand.execute().toString should be("Goodbye!")
    }
  }

}
