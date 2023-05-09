package controller.command.commands

import model.game.gamestate.GameStateManager
import model.game.gamestate.gamestates.{RunningState, WaitForUserConfirmation}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*


case class UserDeclineCommandSpec() extends AnyWordSpec:
  "A UserDeclineCommand" should {
    "Return to GameState Running if currently in GameState WaitForUserConfirmation" in {
      val gsm = GameStateManager(gameState = WaitForUserConfirmation())
      UserDeclineCommand(gsm).execute().toString should be("End round aborted")
    }

    "Do nothing if currently in any other GameState than WaitForUserConfirmation" in {
      val gsm = GameStateManager(gameState = RunningState())
      UserDeclineCommand(gsm).execute().toString should be("")
    }
  }
