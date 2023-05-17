package model.game.gamestate

import model.game.{GameValues, Round}
import model.game.gamestate.GameStateStringFormatter
import model.game.map.GameMap
import model.game.playervalues.PlayerValues
import model.game.purchasable.building.{EnergyGrid, Mine}
import model.game.purchasable.technology.{AdvancedMaterials, Polymer}
import model.game.purchasable.units.{Corvette, Cruiser}
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Energy, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.io.AnsiColor

class GameStateStringFormatterSpec extends AnyWordSpec {

  "The StringRepresentation (basically gameStateToString)" when {
    val gsm: GameStateManager = GameStateManager()
    "Asked for a 'separator'" should {
      "return a separator string used in the overview" in {
        GameStateStringFormatter(gsm = gsm).separator() should be(" |----| ")
      }
      "return a separator string with custom length" in {
        GameStateStringFormatter(gsm = gsm).separator(1) should be(" |-| ")
      }
    }
    "Asked for a 'vertBar'" should {
      "return a vertical bar used in the overview" in {
        GameStateStringFormatter(gsm = gsm).vertBar() should be("=" * 30)
      }
      "return a vertical bar with custom length" in {
        GameStateStringFormatter(gsm = gsm).vertBar(1) should be("=")
      }
    }
    "Asked for an 'overview'" should {
      "return a formatted overview containing round, funds and research" in {
        val gsm = GameStateManager(playerValues = PlayerValues(resourceHolder = ResourceHolder(descriptor = "Balance",
          energy = Energy(100),
          researchPoints = ResearchPoints(100))))
        val f = GameStateStringFormatter(gsm = gsm).overview()
        f.contains("Energy: 100") should be(true)
        f.contains("Minerals: 0") should be(true)
        f.contains("Alloys: 0") should be(true)
        f.contains("Research Points: 100") should be(true)
        f.contains("Capacity: 0/3 ") should be(true)
        f.contains(AnsiColor.BLUE + "[0-A]" + AnsiColor.RESET + "---") should be(true)
        f.contains("---" + AnsiColor.RED + "[6-T]" + AnsiColor.RESET) should be(true)
      }
    }
    "Asked for a 'invalidInputResponse'" should {
      "return the message and a string to explain what to do" in {
        GameStateStringFormatter(gsm = gsm)
          .invalidInputResponse("this is a test") should be("this is a test - invalid\nEnter help to get an " +
          "overview of all available commands")
      }
    }
    "Asked for a 'EmptyResponse'" should {
      "return an empty string" in {
        GameStateStringFormatter(gsm = gsm)
          .empty should be("")
      }
    }
    "Asked for an overview" should {
      "show a list of buildings under construction" in {
        val pV = PlayerValues(listOfBuildingsUnderConstruction = List(Mine(), Mine(), EnergyGrid()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Ongoing Production:") should be(true)
        f.contains("Ongoing construction:") should be(true)
        f.contains("Mine") should be(true)
        f.contains("Energy Grid") should be(true)
      }
      "show a list of technologies currently researched" in {
        val pV = PlayerValues(listOfTechnologiesCurrentlyResearched = List(Polymer(), Polymer(), AdvancedMaterials()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Ongoing Production:") should be(true)
        f.contains("Ongoing research:") should be(true)
        f.contains("Polymer") should be(true)
        f.contains("Advanced Materials") should be(true)
      }
      "show a list of units currently under construction" in {
        val pV = PlayerValues(listOfUnitsUnderConstruction = List(Corvette(), Cruiser(), Corvette()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Ongoing Production:") should be(true)
        f.contains("Ongoing recruitment:") should be(true)
        f.contains("Corvette") should be(true)
        f.contains("Cruiser") should be(true)
      }
      "show a list of player owned buildings" in {
        val pV = PlayerValues(listOfBuildings = List(Mine(), Mine(), EnergyGrid()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Inventory:") should be (true)
        f.contains("Buildings") should be(true)
        f.contains("Mine x 2") should be(true)
        f.contains("Energy Grid x 1") should be(true)
      }
      "show a list of player owned technologies" in {
        val pV = PlayerValues(listOfTechnologies = List(Polymer(), Polymer(), AdvancedMaterials()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Inventory:") should be (true)
        f.contains("Technologies:") should be(true)
        f.contains("Polymer x 2") should be(true)
        f.contains("Advanced Materials x 1") should be(true)
      }
      "show a list of player owned units" in {
        val pV = PlayerValues(listOfUnits = List(Corvette(), Cruiser(), Corvette()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Inventory:") should be (true)
        f.contains("Units:") should be(true)
        f.contains("Corvette x 2") should be(true)
        f.contains("Cruiser x 1") should be(true)
      }
      "show a list of player owned unit" in {
        val pV = PlayerValues(listOfUnits = List(Corvette()))
        val f = GameStateStringFormatter(gsm = GameStateManager(playerValues = pV)).overview()
        f.contains("Inventory:") should be(true)
        f.contains("Units:") should be(true)
        f.contains("Corvette x 1") should be(true)
      }
    }
  }
}
