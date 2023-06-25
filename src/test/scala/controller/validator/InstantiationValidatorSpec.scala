package controller.validator

import controller.command.ICommand
import controller.command.commands.*
import utils.DefaultValueProvider.given_IGameValues
import controller.newInterpreter.CommandTokenizer
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.utilities.ResourceHolder
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class InstantiationValidatorSpec extends AnyWordSpec {

  val player: PlayerValues =
    PlayerValues(resourceHolder =
      ResourceHolder(energy = Energy(1000), minerals = Minerals(1000), alloys = Alloys(1000)))
  val gsm: GameStateManager = GameStateManager(playerValues = Vector(player))

  "The InstantiationValidator" when {

    "initialized with Command 'build'" should {

      "return a MessageCommand with 'build help' content if string input consists of build only" in {
        val input = "build"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a MessageCommand with 'build help' content if string input consists of build help" in {
        val input = "build -help"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a BuildCommand if string input consists of build with valid building" in {
        val input = "build research lab 0-0"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[BuildCommand]
      }

      "return a MessageCommand with 'building does not exist' content if string input consists of " +
        "build with invalid building name" in {
        val input = "build test building"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
    }

    "initialized with Command 'research'" should {

      "return a MessageCommand with 'research help' content if string input consists of research only" in {
        val input = "research"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a MessageCommand with 'research help' content if string input consists of research help" in {
        val input = "research -help"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a ResearchCommand if string input consists of research with valid technology" in {
        val input = "research polymer"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[ResearchCommand]
      }

      "return a MessageCommand with 'technology does not exist' content if string input consists of " +
        "research with invalid technology name" in {
        val input = "research test technology"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }
    }

    "initialized with Command 'recruit'" should {
      "return a MessageCommand with 'recruit help' content if string input consists of recruit only" in {
        val input = "recruit"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a MessageCommand with 'recruit help' content if string input consists of recruit help" in {
        val input = "recruit -help"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a RecruitCommand if string input consists of recruit with valid technology" in {
        val input = "recruit corvette 1 0-0"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[RecruitCommand]
      }

      "return a RecruitCommand if string input consists of recruit with valid technology reversed input order" in {
        val input = "recruit 1 corvette 0-0"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[RecruitCommand]
      }


      "return a MessageCommand with 'unit does not exist' content if string input consists of " +
        "recruit with invalid unit name" in {
        val input = "recruit test unit"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a MessageCommand with content if string input consists of " +
        "recruit with unidentified commands but without quantity and without unit name" in {
        val input = "recruit hey hey hey"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a MessageCommand with content if string input consists of " +
        "recruit with GameObject of wrong type" in {
        val input = "recruit research lab"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[MessageCommand]
      }

      "return a MessageCommand with content if string input consists of " +
        "recruit with invalid subcommand " in {
        val input = "recruit -research"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[InvalidCommand]
      }

      "return a MessageCommand with content if string input consists of " +
        "recruit with gameobject, subcommand and unidentified" in {
        val input = "recruit -help corvette test"
        val instantiationValidator = InstantiationValidator(input, gsm)
        val res = instantiationValidator.validate(CommandTokenizer().parseInput(input).input).toOption.get
        res shouldBe a[Option[ICommand]]
        res.get shouldBe a[InvalidCommand]
      }
    }
  }
}
