package controller.validator.validatorutils

import controller.newInterpreter.{TokenizedInput, CommandType, CommandTokenizer, InterpretedCommand}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ValidatorUtilsSpec extends AnyWordSpec {

  "The ValidatorUtils" should {
    "be used to filter CombinedExpressions" when {
      "CombinedExpression contains nothing should return None" in {
        val combinedExpression = TokenizedInput(Vector(), String())
        ValidatorUtils().findCommandFirst(combinedExpression.input) should be(None)
      }

      "CombinedExpression contains Command list should return Some(CommandType)" in {
        val combinedExpression = TokenizedInput(CommandTokenizer().parseInput("list").input, "list")
        ValidatorUtils().findCommandFirst(combinedExpression.input) should be(Some(CommandType.LIST))
      }
    }
  }

}
