package controller.command.commands

import model.game.Round
import model.game.gamestate.{GameState, GameStateManager, GameStateStringFormatter, IGameStateManager}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class UserResponseCommandSpec extends AnyWordSpec {

  "The UserResponseCommand" should {
    "map any input beginning with y or Y to endRoundConfirmation which should do a reset if gamestate != " +
      "to END_ROUND_REQUEST" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val resp1Command: UserResponseCommand = UserResponseCommand("y", gameStateManager)
      val resp2Command: UserResponseCommand = UserResponseCommand("Y", gameStateManager)
      val resp3Command: UserResponseCommand = UserResponseCommand("Yas", gameStateManager)
      resp1Command.execute().gameState should be(GameState.RUNNING)
      resp1Command.execute().toString should be(GameStateStringFormatter().empty)
      resp2Command.execute().gameState should be(GameState.RUNNING)
      resp2Command.execute().toString should be(GameStateStringFormatter().empty)
      resp3Command.execute().gameState should be(GameState.RUNNING)
      resp3Command.execute().toString should be(GameStateStringFormatter().empty)
    }
    "map any input not beginning with y or Y to resetGameState even if GameState == GameState.END_ROUND_REQUEST" in {
      val gameStateManager: IGameStateManager = GameStateManager()
      val resp1Command: UserResponseCommand = UserResponseCommand("no", gameStateManager)
      val resp2Command: UserResponseCommand = UserResponseCommand("", gameStateManager)
      val resp3Command: UserResponseCommand = UserResponseCommand("lol", gameStateManager)
      resp1Command.execute().gameState should be(GameState.RUNNING)
      resp1Command.execute().toString should be(GameStateStringFormatter().empty)
      resp2Command.execute().gameState should be(GameState.RUNNING)
      resp2Command.execute().toString should be(GameStateStringFormatter().empty)
      resp3Command.execute().gameState should be(GameState.RUNNING)
      resp3Command.execute().toString should be(GameStateStringFormatter().empty)
    }
    "map any input beginning with y or Y to endRoundConfirmation which should enter next round if gamestate == " +
      "to END_ROUND_REQUEST" in {
      val gameStateManager: IGameStateManager = GameStateManager(gameState = GameState.END_ROUND_REQUEST)
      val resp1Command: UserResponseCommand = UserResponseCommand("y", gameStateManager)
      val resp2Command: UserResponseCommand = UserResponseCommand("Y", gameStateManager)
      val resp3Command: UserResponseCommand = UserResponseCommand("Yas", gameStateManager)
      resp1Command.execute().gameState should be(GameState.RUNNING)
      resp1Command.execute().toString should be(GameStateStringFormatter(playerValues = gameStateManager.playerValues).overview(round = Round(2)))
      resp2Command.execute().gameState should be(GameState.RUNNING)
      resp2Command.execute().toString should be(GameStateStringFormatter(playerValues = gameStateManager.playerValues).overview(round = Round(2)))
      resp3Command.execute().gameState should be(GameState.RUNNING)
      resp3Command.execute().toString should be(GameStateStringFormatter(playerValues = gameStateManager.playerValues).overview(round = Round(2)))
    }
  }

}
