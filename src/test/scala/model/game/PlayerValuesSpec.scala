package model.game

import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer}
import model.core.gameobjects.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer}
import model.core.mechanics.fleets.components.units.IUnit
import model.game.playervalues.PlayerValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class PlayerValuesSpec extends AnyWordSpec {
  "The PlayerValues" should {
    "be initialized with EmptyLists" in {
      val playerValues: PlayerValues = PlayerValues()
      playerValues.listOfTechnologies shouldBe a [Vector[_]]
      playerValues.listOfTechnologies should be(empty)
    }
    "be re-initializable" in {
      val playerValues: PlayerValues
      = PlayerValues(listOfTechnologies = Vector(Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion()))
      playerValues.listOfTechnologies shouldBe a[Vector[_]]
      playerValues.listOfTechnologies should not be(empty)
    }
    "the lists should return new lists if something is appended re-initializable" in {
      val playerValues: PlayerValues = PlayerValues()
      playerValues.listOfTechnologies.:+(AdvancedMaterials()) should be(Vector[ITechnology](AdvancedMaterials()))
    }
  }
}
