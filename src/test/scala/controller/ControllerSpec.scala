package controller

import controller.command.ICommand
import controller.command.commands.*
import utils.DefaultValueProvider.given_IGameValues
import controller.newInterpreter.tokens.CommandToken
import controller.newInterpreter.{CommandTokenizer, InterpretedCommand, InterpretedGameObject, TokenizedInput}
import controller.validator.ValidationHandler
import model.core.gameobjects.purchasable.units.Cruiser
import model.game.gamestate.GameStateManager
import model.game.gamestate.gamestates.{ExitedState, RunningState, WaitForUserConfirmation}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ControllerSpec extends AnyWordSpec {

  val gsm: GameStateManager = GameStateManager()

  "The Controller's" should {
    val controller: Controller = Controller()

    "process input method should return true if game is running" in {
      val expr: TokenizedInput = CommandTokenizer().parseInput("this is a simple test (should be invalid)")
      val testInput: ICommand = ValidationHandler(gsm).handle(expr).get
      controller.processInput(testInput) should be(true)
    }
  }

  "The processInput method" should {
    "return the game state according to the input" in {
      val controller: Controller = Controller()
      val expr1: TokenizedInput = CommandTokenizer().parseInput("done")
      val expr2: TokenizedInput = CommandTokenizer().parseInput("test")
      val expr3: TokenizedInput = CommandTokenizer().parseInput("exit")
      val endRoundExp: ICommand = ValidationHandler(gsm).handle(expr1).get
      val invalidExp: ICommand = ValidationHandler(gsm).handle(expr2).get
      val endGameExp: ICommand = ValidationHandler(gsm).handle(expr3).get

      controller.processInput(endRoundExp) should be (true)
      controller.processInput(invalidExp) should be (true)
      controller.processInput(endGameExp) should be (false)
    }

    "should work with CombinedExpression as well as commands" in {
      val controller: Controller = Controller()
      val expr: TokenizedInput = CommandTokenizer().parseInput("done")
      controller.processInput(expr) should be (true)
    }
  }

  "The toString method" should {
    "return the toString representation of the GameStateManager depending on the input" in {
      val controller: Controller = Controller()
      val expr: TokenizedInput = CommandTokenizer().parseInput("test")
      val command: ICommand = ValidationHandler(gsm).handle(expr).get
      controller.processInput(command)
      controller.toString().contains("Invalid Input: 'test'") should be(true)
    }
  }
}