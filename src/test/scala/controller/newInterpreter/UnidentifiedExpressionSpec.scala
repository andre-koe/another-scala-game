package controller.newInterpreter

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
class UnidentifiedExpressionSpec extends AnyWordSpec {

  "The UnidentifiedExpression" should {
    "Take a String input and store it" in {
      UnidentifiedExpression("some string").interpret() should be("some string")
    }
  }

}
