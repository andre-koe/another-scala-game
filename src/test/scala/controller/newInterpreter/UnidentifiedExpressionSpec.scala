package controller.newInterpreter

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._
class UnidentifiedExpressionSpec extends AnyWordSpec {

  "The UnidentifiedTokenizedInput" should {
    "Take a String input and store it" in {
      UnidentifiedTokenizedInput("some string").interpret() should be("some string")
    }
  }

}
