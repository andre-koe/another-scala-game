package model.game.gamestate

import model.game.{GameValues, PlayerValues, Round}
import model.game.gamestate.GameStateStringFormatter
import model.game.purchasable.building.Mine
import model.game.purchasable.technology.AdvancedMaterials
import model.game.purchasable.units.Corvette
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GameStateStringFormatterSpec extends AnyWordSpec {

  "The StringRepresentation (basically gameStateToString)" when {
    val gameStateManager: GameStateManager = GameStateManager()
    "Asked for a 'separator'" should {
      "return a separator string used in the overview" in {
        GameStateStringFormatter(gameStateManager = gameStateManager).separator() should be(" |----| ")
      }
      "return a separator string with custom length" in {
        GameStateStringFormatter(gameStateManager = gameStateManager).separator(1) should be(" |-| ")
      }
    }
    "Asked for a 'vertBar'" should {
      "return a vertical bar used in the overview" in {
        GameStateStringFormatter(gameStateManager = gameStateManager).vertBar() should be("=" * 30)
      }
      "return a vertical bar with custom length" in {
        GameStateStringFormatter(gameStateManager = gameStateManager).vertBar(1) should be("=")
      }
    }
    "Asked for an 'overview'" should {
      "return a formatted overview containing round, funds and research" in {
        GameStateStringFormatter(round = Round(), playerValues = PlayerValues(resourceHolder =
          ResourceHolder(descriptor = "Balance",
            energy = Energy(100),
            researchPoints = ResearchPoints(100))), gameStateManager = gameStateManager).overview() should be(
            "===============================================================================" + "\n" +
            " [Round: 1] |----| Total Balance: [Energy: 100] [Research Points: 100] |----|  " + "\n" +
            "===============================================================================" + "\n")
      }
    }
    "Asked for a 'invalidInputResponse'" should {
      "return the message and a string to explain what to do" in {
        GameStateStringFormatter(gameStateManager = gameStateManager)
          .invalidInputResponse("this is a test") should be("this is a test - invalid\nEnter help to get an " +
          "overview of all available commands")
      }
    }
  }
}
