package model.interpreter.userexpressions

import controller.command.commands.{InvalidCommand, MessageCommand, UserAcceptCommand, UserDeclineCommand}
import model.game.gamestate.{GameStateManager, GameStateStringFormatter}
import org.scalatest.matchers.should.Matchers.be
import org.scalatest.wordspec.AnyWordSpec

class UserResponseExpressionTest extends AnyWordSpec {
  "The UserResponseExpressionTest" when {
    val gameStateManager: GameStateManager = GameStateManager()

    "given the inputs y | yes should return an UserResponseCommand" in {
      val input1 = List("y")
      val input2 = List("yes")

      val userResponseExp1: UserResponseExpression = UserResponseExpression(input1)
      val userResponseExp2: UserResponseExpression = UserResponseExpression(input2)

      userResponseExp1.interpret(gameStateManager) shouldBe a[UserAcceptCommand]
      userResponseExp2.interpret(gameStateManager) shouldBe a[UserAcceptCommand]
    }

    "given the inputs n | no should return an UserResponseCommand" in {
      val input1 = List("n")
      val input2 = List("no")

      val userResponseExp1: UserResponseExpression = UserResponseExpression(input1)
      val userResponseExp2: UserResponseExpression = UserResponseExpression(input2)

      userResponseExp1.interpret(gameStateManager) shouldBe a[UserDeclineCommand]
      userResponseExp2.interpret(gameStateManager) shouldBe a[UserDeclineCommand]
    }

    "given any other inputs should return an InvalidCommand" in {
      val input1 = List("something else")
      val input2 = List("")

      val userResponseExp1: UserResponseExpression = UserResponseExpression(input1)
      val userResponseExp2: UserResponseExpression = UserResponseExpression(input2)

      userResponseExp1.interpret(gameStateManager) shouldBe a[MessageCommand]
      userResponseExp2.interpret(gameStateManager) shouldBe a[MessageCommand]
    }
  }
}
