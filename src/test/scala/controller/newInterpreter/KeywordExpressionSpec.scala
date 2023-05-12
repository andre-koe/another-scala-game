package controller.newInterpreter

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class KeywordExpressionSpec extends AnyWordSpec {

  "A KeywordExpression" should {

    "take a string and return the appropriate KeywordType" when {

      "input 'to' returns TO" in {
        val keywordExpression = KeywordExpression("to")
        keywordExpression.interpret() should be(KeywordType.TO)
      }

      "input '>' returns TO" in {
        val keywordExpression = KeywordExpression(">")
        keywordExpression.interpret() should be(KeywordType.TO)
      }

      "input 'from' returns FROM" in {
        val keywordExpression = KeywordExpression("from")
        keywordExpression.interpret() should be(KeywordType.FROM)
      }

      "input '<' returns FROM" in {
        val keywordExpression = KeywordExpression("<")
        keywordExpression.interpret() should be(KeywordType.FROM)
      }

      "input 'with' returns WITH" in {
        val keywordExpression = KeywordExpression("with")
        keywordExpression.interpret() should be(KeywordType.WITH)
      }

    }
  }

}