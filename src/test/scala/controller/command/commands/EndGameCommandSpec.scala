package controller.command.commands

import model.game.gamestate.{GameStateManager, IGameStateManager, GameState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class EndGameCommandSpec extends AnyWordSpec {

  "The EndGameCommand" should {
    "set the GameStateManagers gameState to EXIT and let him say goodbye" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val endGameCommand: EndGameCommand = EndGameCommand(gameStateManager)
      endGameCommand.execute().gameState should be(GameState.EXITED)
      endGameCommand.execute().toString should be("Goodbye!")
    }
  }

}
