package controller.validator

import controller.command.commands.{InvalidCommand, MessageCommand}
import controller.newInterpreter.{TokenizedInput, CommandType, CommandTokenizer, InterpretedCommand, InterpretedGameObject, InterpretedSubcommand, InterpretedUnidentified}
import controller.newInterpreter.CommandType.*
import controller.newInterpreter.tokens.CommandToken
import model.core.gameobjects.purchasable.building.ResearchLab
import model.core.gameobjects.purchasable.technology.AdvancedMaterials
import model.core.gameobjects.purchasable.units.Cruiser
import model.game.gamestate.GameStateManager
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor

class HelpValidatorSpec extends AnyWordSpec {

  "A HelpValidator" should {
    val gsm = GameStateManager()

    "turn a string into the appropriate HelpCommand" when {

      "input is empty should map to HelpCommand General" in {
        val expr = CommandTokenizer().parseInput("help")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString.contains("The following actions should be implemented") should be (true)
      }

      "input is help help should map to HelpCommand General" in {
        val expr = CommandTokenizer().parseInput("help help")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString.contains("The following actions should be implemented") should be(true)
      }

      "input contains 'build.*' should map to HelpCommand Build" in {
        val expr = CommandTokenizer().parseInput("help -build")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString should be("A building can impact the game in various ways, such as increasing " +
          "research output, providing energy, or increasing unit capacity.")
      }

      "input contains 'tech.*' should map to HelpCommand Tech" in {
        val expr = CommandTokenizer().parseInput("help -technology")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString should be("A technology in the game that can be researched by " +
        "players to unlock new abilities, units, or buildings.")
      }

      "input contains 'recruit.*' should map to HelpCommand Recruitment" in {
        val expr = CommandTokenizer().parseInput("help -recruitment")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString should be("A unit can be used to fight over sectors and conquer new sectors.")
      }

      "input contains valid Unit name such as 'cruiser' should map to HelpCommand Specific" in {
        val expr = CommandTokenizer().parseInput("help cruiser")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString should be("Cruiser")
      }

      "input contains valid Building name such as 'research lab' should map to HelpCommand Specific" in {
        val expr = CommandTokenizer().parseInput("help research lab")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString should be("Research Lab")
      }

      "input contains valid technology name such as 'advanced materials' should map to HelpCommand Specific" in {
        val expr = CommandTokenizer().parseInput("help advanced materials")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get
          .execute().toString should be("Advanced Materials")
      }

      "input contains invalid game object name such as 'test object' should map to MessageCommand" in {
        val expr = CommandTokenizer().parseInput("help test object")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[MessageCommand]
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get.execute()
          .toString should be(s"${AnsiColor.GREEN}Could not find any information on 'test object'${AnsiColor.RESET}")
      }

      "input contains game object name, subcommand and unidentified should map to InvalidCommand" in {
        val expr = CommandTokenizer().parseInput("help -test cruiser hello")
        HelpValidator(expr.orig, gsm).validate(expr.input).toOption.get.get shouldBe a[InvalidCommand]
      }
    }
  }
}
