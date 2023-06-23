package controller.newInterpreter

import controller.newInterpreter.tokens.UnidentifiedToken
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
class UnidentifiedExpressionSpec extends AnyWordSpec {

  "The UnidentifiedTokenizedInput" should {
    "Take a String input and store it" in {
      UnidentifiedToken("some string").interpret() should be("some string")
    }
  }

}
