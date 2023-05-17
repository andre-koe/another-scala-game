package controller.validator

import controller.command.ICommand
import controller.command.commands.{MessageCommand, SellCommand}
import controller.newInterpreter.ExpressionParser
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import model.game.purchasable.building.Mine
import model.game.purchasable.technology.Polymer
import model.game.purchasable.units.Cruiser
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class SellValidatorSpec extends AnyWordSpec {

  "The SellValidator" should {
    val playerValues = PlayerValues(
      listOfUnits = List(Cruiser(), Cruiser(), Cruiser()),
      listOfBuildings = List(Mine(), Mine(), Mine()),
      listOfTechnologies = List(Polymer(), Polymer(), Polymer())
    )
    val gsm = GameStateManager(playerValues = playerValues)

    "turn an input string with command sell into the appropriate command" when {
      "input is sell should return message command" in {
        val input = "sell"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell -help should return message command" in {
        val input = "sell -help"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell -test should return message command" in {
        val input = "sell -test"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell -test -or -something -else should return message command" in {
        val input = "sell -test -or -something -else"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell cruiser should return sell command" in {
        val input = "sell cruiser"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[SellCommand]
      }

      "input is sell mine should return sell command" in {
        val input = "sell mine"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[SellCommand]
      }

      "input is sell polymer should return message command" in {
        val input = "sell polymer"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell battleship should return message command" in {
        val input = "sell battleship"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell cruiser 6 should return message command" in {
        val input = "sell cruiser 6"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell cruiser -help should return message command" in {
        val input = "sell cruiser -help"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell testobject should return message command" in {
        val input = "sell testobject"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(ExpressionParser().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
    }
  }

}
