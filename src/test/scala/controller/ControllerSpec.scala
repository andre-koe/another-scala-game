package controller

import controller.command.ICommand
import controller.command.commands.*
import controller.newInterpreter.{CombinedExpression, CommandExpression, ExpressionParser, InterpretedCommand, InterpretedGameObject}
import controller.validator.ValidationHandler
import model.game.gamestate.GameStateManager
import model.game.gamestate.gamestates.{ExitedState, RunningState, WaitForUserConfirmation}
import model.game.purchasable.units.Cruiser
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ControllerSpec extends AnyWordSpec {

  val gsm: GameStateManager = GameStateManager()

  "The Controller's" should {
    val controller: Controller = Controller()

    "process input method should return true if game is running" in {
      val expr: CombinedExpression = ExpressionParser().parseInput("this is a simple test (should be invalid)")
      val testInput: ICommand = ValidationHandler(gsm).handle(expr).get
      controller.processInput(testInput) should be(true)
    }
  }

  "The processInput method" should {
    "return the game state according to the input" in {
      val controller: Controller = Controller()
      val expr1: CombinedExpression = ExpressionParser().parseInput("done")
      val expr2: CombinedExpression = ExpressionParser().parseInput("test")
      val expr3: CombinedExpression = ExpressionParser().parseInput("exit")
      val endRoundExp: ICommand = ValidationHandler(gsm).handle(expr1).get
      val invalidExp: ICommand = ValidationHandler(gsm).handle(expr2).get
      val endGameExp: ICommand = ValidationHandler(gsm).handle(expr3).get

      controller.processInput(endRoundExp) should be (true)
      controller.processInput(invalidExp) should be (true)
      controller.processInput(endGameExp) should be (false)
    }

    "should work with CombinedExpression as well as commands" in {
      val controller: Controller = Controller()
      val expr: CombinedExpression = ExpressionParser().parseInput("done")
      controller.processInput(expr) should be (true)
    }
  }

  "The toString method" should {
    "return the toString representation of the GameStateManager depending on the input" in {
      val controller: Controller = Controller()
      val expr: CombinedExpression = ExpressionParser().parseInput("help -building")
      val command: ICommand = ValidationHandler(gsm).handle(expr).get
      controller.processInput(command)
      controller.toString().contains("Invalid Input: 'test'") should be(true)
    }
  }
}