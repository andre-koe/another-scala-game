package controller

import controller.newInterpreter.{CombinedExpression, ExpressionParser}
import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class CombinedExpressionAdapterSpec extends AnyWordSpec {

  "The CombinesExpressionAdapter" when {
    val gsm = GameStateManager()
    "execute is invoked return a GameStateManager" when  {

      "CombinedExpression is invalid" in {
        val expr = ExpressionParser().parseInput("this should be invalid")
        CombinedExpressionAdapter(expr, gsm).execute() shouldBe a[GameStateManager]
      }

      "CombinedExpression is valid" in {
        val expr = ExpressionParser().parseInput("help")
        CombinedExpressionAdapter(expr, gsm).execute() shouldBe a[GameStateManager]
      }

    }
  }

}
