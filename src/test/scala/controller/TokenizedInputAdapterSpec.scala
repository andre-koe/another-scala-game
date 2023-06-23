package controller

import controller.adapters.TokenizedInputToCommandAdapter
import controller.newInterpreter.{CommandTokenizer, TokenizedInput}
import model.game.gamestate.GameStateManager
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class TokenizedInputAdapterSpec extends AnyWordSpec {

  "The CombinesExpressionAdapter" when {
    val gsm = GameStateManager()
    "execute is invoked return a GameStateManager" when  {

      "CombinedExpression is invalid" in {
        val expr = CommandTokenizer().parseInput("this should be invalid")
        TokenizedInputToCommandAdapter(expr, gsm).execute() shouldBe a[GameStateManager]
      }

      "CombinedExpression is valid" in {
        val expr = CommandTokenizer().parseInput("help")
        TokenizedInputToCommandAdapter(expr, gsm).execute() shouldBe a[GameStateManager]
      }

    }
  }

}
