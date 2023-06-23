package controller.newInterpreter

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CommandTokenizerSpec extends AnyWordSpec {

  "The ExpressionParser is responsible for tokenizing and interpreting user inputs" should {
    "take a String and return the appropriate CombinedExpression" in {
      CommandTokenizer().parseInput(String()) shouldBe a[TokenizedInput]
      CommandTokenizer().parseInput(String()).input.length should be(0)
    }

    "take a String (number) and return the appropriate CombinedExpression" in {
      CommandTokenizer().parseInput("11") shouldBe a[TokenizedInput]
      CommandTokenizer().parseInput("11").input.length should be(1)
    }

  }

}
