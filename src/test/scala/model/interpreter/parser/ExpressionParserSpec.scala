package model.interpreter.parser

import model.interpreter.parser.ExpressionParser
import model.interpreter.userexpressions.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ExpressionParserSpec extends AnyWordSpec:
  
  "The ExpressionParser" should {
    "turn a String into the appropriate Expression" in {
      val parser: ExpressionParser = ExpressionParser()
      
      "map empty input to EmptyExpression" in {
        val testInput: String = ""
        parser.parse(testInput) shouldBe a[EmptyExpression]
      }
      
      "map build input to BuildExpression" in {
        val testInput: String = "build"
        parser.parse(testInput) shouldBe a[BuildExpression]
      }
      
      "map research input to ResearchExpression" in {
        val testInput: String = "research"
        parser.parse(testInput) shouldBe a[ResearchExpression]
      }
      
      "map recruit input to RecruitExpression" in {
        val testInput: String = "recruit"
        parser.parse(testInput) shouldBe a[RecruitExpression]
      }
      
      "map show input to ShowExpression" in {
        val testInput: String = "show"
        parser.parse(testInput) shouldBe a[ShowExpression]
      }
      
      "map list input to ListExpression" in {
        val testInput: String = "list"
        parser.parse(testInput) shouldBe a[ListExpression]
      }
      
      "map move input to MoveExpression" in {
        val testInput: String = "move x y"
        parser.parse(testInput) shouldBe a[MoveExpression]
      }
      
      "map save input to SaveExpression" in {
        val testInput: String = "save"
        parser.parse(testInput) shouldBe a[SaveExpression]
      }
      
      "map load input to LoadExpression" in {
        val testInput: String = "load"
        parser.parse(testInput) shouldBe a[LoadExpression]
      }
      
      "map sell input to SellExpression" in {
        val testInput: String = "sell"
        parser.parse(testInput) shouldBe a[SellExpression]
      }
      
      "map help (help / h) input to HelpExpression" in {
        val testInput: String = "help"
        val testInput2: String = "H"
        parser.parse(testInput) shouldBe a[HelpExpression]
        parser.parse(testInput2) shouldBe a[HelpExpression]
      }
      
      "map exit (exit / quit) input to ExitExpression" in {
        val testInput: String = "exit"
        val testInput2: String = "quit"
        parser.parse(testInput) shouldBe a[EndGameExpression]
        parser.parse(testInput2) shouldBe a[EndGameExpression]
      }
      
      "map invalid or undefined input to InvalidExpression" in {
        val testInput: String = "test"
        parser.parse(testInput) shouldBe a[InvalidExpression]
      }
      
      "map (yes / y / n / no) to UserResponseExpression" in {
        parser.parse("yes") shouldBe a[UserResponseExpression]
        parser.parse("y") shouldBe a[UserResponseExpression]
        parser.parse("n") shouldBe a[UserResponseExpression]
        parser.parse("no") shouldBe a[UserResponseExpression]

      }
      
      "map done to EndRoundExpression" in {
        parser.parse("done") shouldBe a[EndRoundExpression]
      }
    }
  }
