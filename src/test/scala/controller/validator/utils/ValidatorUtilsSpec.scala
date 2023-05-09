package controller.validator.utils

import controller.newInterpreter.{CombinedExpression, CommandType, ExpressionParser, InterpretedCommand}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ValidatorUtilsSpec extends AnyWordSpec {

  "The ValidatorUtils" should {
    "be used to filter CombinedExpressions" when {
      "CombinedExpression contains nothing should return None" in {
        val combinedExpression = CombinedExpression(Vector(), String())
        ValidatorUtils().findCommandFirst(combinedExpression.input) should be(None)
      }

      "CombinedExpression contains Command list should return Some(CommandType)" in {
        val combinedExpression = CombinedExpression(ExpressionParser().parseInput("list").input, "list")
        ValidatorUtils().findCommandFirst(combinedExpression.input) should be(Some(CommandType.LIST))
      }
    }
  }

}
