package controller.newInterpreter

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ExpressionParserSpec extends AnyWordSpec {

  "The ExpressionParser is responsible for tokenizing and interpreting user inputs" should {
    "take a String and return the appropriate CombinedExpression" in {
      ExpressionParser().parseInput(String()) shouldBe a[CombinedExpression]
      ExpressionParser().parseInput(String()).input.length should be(0)
    }

    "take a String (number) and return the appropriate CombinedExpression" in {
      ExpressionParser().parseInput("11") shouldBe a[CombinedExpression]
      ExpressionParser().parseInput("11").input.length should be(1)
    }

  }

}
