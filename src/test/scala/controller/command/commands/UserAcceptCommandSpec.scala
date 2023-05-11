package controller.command.commands

import model.game.gamestate.GameStateManager
import model.game.gamestate.gamestates.{RunningState, WaitForUserConfirmation}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class UserAcceptCommandSpec extends AnyWordSpec:
  "A UserAcceptCommand" should {
    "Lead to the start of a new round if the Current GameState is WaitForUserConfirmation" in {
      val gsm = GameStateManager(gameState = WaitForUserConfirmation())
      UserAcceptCommand(gsm = gsm).execute().round.value should be(2)
    }

    "Not change the GameState if initial GameState is anything else than WaitForUserConfirmation" in {
      val gsm = GameStateManager(gameState = RunningState())
      UserAcceptCommand(gsm = gsm).execute().round.value should be(1)
    }
  }
