package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, MessageCommand, ShowCommand}
import controller.newInterpreter.CommandTokenizer
import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ShowValidatorSpec extends AnyWordSpec {

  "The ShowValidator" should {
    val gsm = GameStateManager()
    "return the appropriate ShowCommand" when {
      "show -overview is entered" in {
        val input = "show -overview"
        val listValidator = ShowValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ShowCommand]
      }
      "show -help is entered" in {
        val input = "show -help"
        val listValidator = ShowValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
      "show is entered" in {
        val input = "show"
        val listValidator = ShowValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
      "show -test is entered" in {
        val input = "show -test"
        val listValidator = ShowValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
      "show -test -snack is entered" in {
        val input = "show -test -snack"
        val listValidator = ShowValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
    }
  }

}
