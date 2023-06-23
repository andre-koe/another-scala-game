package controller.validator

import controller.command.commands.{LoadCommand, MessageCommand, SaveCommand}
import controller.newInterpreter.CommandTokenizer
import utils.DefaultValueProvider.given_IGameValues
import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class FileIOValidatorSpec extends AnyWordSpec {

  "The FileIOValidator" should {
    val gsm = GameStateManager()

    "return the appropriate Command depending on the Input" when {

      "input has InterpretedCommand save" in {
        val expr = CommandTokenizer().parseInput("save")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
      }

      "input has InterpretedCommand save -help" in {
        val expr = CommandTokenizer().parseInput("save -help")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
      }

      "input has InterpretedCommand save -test" in {
        val expr = CommandTokenizer().parseInput("save -test")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
      }

      "input has InterpretedCommand save -f=test.json" in {
        val expr = CommandTokenizer().parseInput("save -f=test.json")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[SaveCommand]
      }

      "input has InterpretedCommand load" in {
        val expr = CommandTokenizer().parseInput("load")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
      }

      "input has InterpretedCommand load -help" in {
        val expr = CommandTokenizer().parseInput("load -help")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
      }

      "input has InterpretedCommand load -test" in {
        val expr = CommandTokenizer().parseInput("load -test")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
      }

      "input has InterpretedCommand load -f=test.json" in {
        val expr = CommandTokenizer().parseInput("load -f=test.json")
        FileIOValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[LoadCommand]
      }
    }
  }

}
