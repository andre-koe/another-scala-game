package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, ListCommand, MessageCommand}
import controller.newInterpreter.CommandTokenizer
import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ListValidatorSpec extends AnyWordSpec {
  "The ListValidator" should {
    val gsm: GameStateManager = GameStateManager()

    "take an input string and turn it into the appropriate ListCommand" when {

      "input list should be list all" in {
        val input = "list"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ListCommand]
      }

      "input list -all should be list all" in {
        val input = "list -all"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ListCommand]
      }

      "input list -tech should list technologies" in {
        val input = "list -tech"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ListCommand]
      }

      "input list -building should list buildings" in {
        val input = "list -building"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ListCommand]
      }

      "input list -unit should list units" in {
        val input = "list -unit"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ListCommand]
      }

      "input list -help should list help" in {
        val input = "list -help"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input list -test should be a MessageCommand" in {
        val input = "list -test"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input list -test -test should be a InvalidCommand" in {
        val input = "list -test -test"
        val listValidator = ListValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[InvalidCommand]
      }

    }
  }
}
