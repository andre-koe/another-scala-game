package controller

import controller.command.commands.*
import model.game.gamestate.gamestates.{WaitForEndRoundConfirmation, ExitedState, RunningState}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ControllerSpec extends AnyWordSpec {

  "The Controller's" should {
    val controller: Controller = Controller()
    "process input method should return true if game is running" in {
      val testInput: String = "Some String to Test"
      controller.processInput(testInput) should be(true)
    }
  }
  "The processInput method" should {
    "return the game state according to the input" in {
      val controller: Controller = Controller()
      controller.processInput("done").isInstanceOf[WaitForEndRoundConfirmation] should be (true)
      controller.processInput("test").isInstanceOf[RunningState] should be (true)
      controller.processInput("exit").isInstanceOf[ExitedState] should be (true)
    }
  }

  "The toString method" should {
    "return the toString representation of the GameStateManager depending on the input" in {
      val controller: Controller = Controller()
      controller.processInput("test")
      controller.toString() should be ("Unknown Input: 'test' - invalid\nEnter help to get an overview of all available commands")
    }
  }
}