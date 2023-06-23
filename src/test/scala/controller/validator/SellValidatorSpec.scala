package controller.validator

import controller.command.ICommand
import controller.command.commands.{MessageCommand, SellCommand}
import controller.newInterpreter.CommandTokenizer
import model.core.board.{IGameBoard, GameBoardBuilder}
import model.core.board.boardutils.Coordinate
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.gameobjects.purchasable.building.{IBuilding, Mine}
import model.core.gameobjects.purchasable.technology.Polymer
import model.core.gameobjects.purchasable.units.Cruiser
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import utils.DefaultValueProvider.given_IGameValues

class SellValidatorSpec extends AnyWordSpec {

  "The SellValidator" should {
    val playerValues = PlayerValues(listOfTechnologies = Vector(Polymer(), Polymer(), Polymer()))
    val gsm = GameStateManager(playerValues = playerValues, gameMap = getGSMWithThingsToSell)

    "turn an input string with command sell into the appropriate command" when {
      "input is sell should return message command" in {
        val input = "sell"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell -help should return message command" in {
        val input = "sell -help"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell -test should return message command" in {
        val input = "sell -test 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell -test -or -something -else should return message command" in {
        val input = "sell -test -or -something -else 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell cruiser should return sell command" in {
        val input = "sell cruiser 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[SellCommand]
      }

      "input is sell mine should return sell command" in {
        val input = "sell mine 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[SellCommand]
      }

      "input is sell polymer should return message command" in {
        val input = "sell polymer 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell battleship should return message command" in {
        val input = "sell battleship 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell cruiser 6 should return message command" in {
        val input = "sell cruiser 6 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell cruiser -help should return message command" in {
        val input = "sell cruiser -help 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "input is sell testobject should return message command" in {
        val input = "sell testobject 0-0"
        val listValidator = SellValidator(input, gsm)
        val res = listValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
    }
  }

  private def getGSMWithThingsToSell: IGameBoard =
    val sector = Sector(location = Coordinate(0, 0), 
      unitsInSector = Vector(Fleet(fleetComponents = Vector(Cruiser(), Cruiser(), Cruiser()))))
    val pSector = PlayerSector(sector = sector, buildingsInSector = Vector(Mine(), Mine(), Mine()))
    GameBoardBuilder().build.updateSector(pSector)
}
