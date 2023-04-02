package model.game.gamestate

import model.game.{GameValues, Round}
import model.countable.{Balance, Research}
import model.game.gamestate.GameStateStringFormatter
import model.purchasable.building.Mine
import model.purchasable.technology.AdvancedMaterials
import model.purchasable.units.Corvette
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GameStateStringFormatterSpec extends AnyWordSpec {

  "The StringRepresentation (basically gameStateToString)" when {
    "Asked for a 'separator'" should {
      "return a separator string used in the overview" in {
        GameStateStringFormatter().separator() should be(" |----| ")
      }
      "return a separator string with custom length" in {
        GameStateStringFormatter().separator(1) should be(" |-| ")
      }
    }
    "Asked for a 'empty'" should {
      "return an empty string" in {
        GameStateStringFormatter().empty should be("")
      }
    }
    "Asked for a 'vertBar'" should {
      "return a vertical bar used in the overview" in {
        GameStateStringFormatter().vertBar() should be("=" * 30)
      }
      "return a vertical bar with custom length" in {
        GameStateStringFormatter().vertBar(1) should be("=")
      }
    }
    "Asked for an 'overview'" should {
      "return a formatted overview containing round, funds and research" in {
        GameStateStringFormatter(Round(), Balance(100), Research()).overview() should be(
          "================================================================================" + "\n" +
          " [Round: 1] |----| [Current balance: 100] |----| [Current research points: 100] " + "\n" +
          "================================================================================")
      }
    }
    "Asked for 'confirmation'" should {
      "return a confirmation response (yes/no) question" in {
        GameStateStringFormatter().askForConfirmation should be("Are you sure? [yes (y) / no (n)]")
      }
    }
    "Asked for a 'goodbyeResponse'" should {
      "return a friendly Goodbye!" in {
        GameStateStringFormatter().goodbyeResponse should be (f"Goodbye!")
      }
    }
    "Asked for a 'invalidInputResponse'" should {
      "return the message and a string to explain what to do" in {
        GameStateStringFormatter()
          .invalidInputResponse("this is a test") should be ("this is a test - invalid\nEnter help to get an " +
          "overview of all available commands")
      }
    }
    "Asked for a 'list' of buildings" should {
      "return the formatted list of prepended building names" in {
        GameStateStringFormatter(gameValues = GameValues(listOfBuildings = List(Mine())))
          .listBuildings should be("==== Buildings ====\n - Mine\n")
      }
    }
    "Asked for a 'list' of units" should {
      "return the formatted list of prepended building names" in {
        GameStateStringFormatter(gameValues = GameValues(listOfUnits = List(Corvette())))
          .listUnits should be("==== Units ====\n - Corvette\n")
      }
    }
    "Asked for a 'list' of technologies" should {
      "return the formatted list of prepended building names" in {
        GameStateStringFormatter(gameValues = GameValues(listOfTechnologies = List(AdvancedMaterials())))
          .listTechnologies should be("==== Technologies ====\n - Advanced Materials\n")
      }
    }
  }
}
