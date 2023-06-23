package controller.newInterpreter

import controller.newInterpreter.tokens.KeywordToken
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class KeywordExpressionSpec extends AnyWordSpec {

  "A KeywordTokenizedInput" should {

    "take a string and return the appropriate KeywordType" when {

      "input 'to' returns TO" in {
        val keywordExpression = KeywordToken("to")
        keywordExpression.interpret() should be(KeywordType.TO)
      }

      "input '>' returns TO" in {
        val keywordExpression = KeywordToken(">")
        keywordExpression.interpret() should be(KeywordType.TO)
      }

      "input 'from' returns FROM" in {
        val keywordExpression = KeywordToken("from")
        keywordExpression.interpret() should be(KeywordType.FROM)
      }

      "input '<' returns FROM" in {
        val keywordExpression = KeywordToken("<")
        keywordExpression.interpret() should be(KeywordType.FROM)
      }

      "input 'with' returns WITH" in {
        val keywordExpression = KeywordToken("with")
        keywordExpression.interpret() should be(KeywordType.WITH)
      }

    }
  }

}
