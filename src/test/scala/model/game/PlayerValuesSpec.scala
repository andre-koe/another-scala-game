package model.game

import model.game.playervalues.PlayerValues
import model.game.purchasable.building.{EnergyGrid, Factory, Hangar, IBuilding, Mine, ResearchLab, Shipyard}
import model.game.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, ITechnology, NanoRobotics, Polymer}
import model.game.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer, IUnit}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class PlayerValuesSpec extends AnyWordSpec {
  "The PlayerValues" should {
    "be initialized with EmptyLists" in {
      val playerValues: PlayerValues = PlayerValues()
      playerValues.listOfUnits shouldBe a [List[_]]
      playerValues.listOfUnits should be(empty)
      playerValues.listOfBuildings shouldBe a [List[_]]
      playerValues.listOfBuildings should be(empty)
      playerValues.listOfTechnologies shouldBe a [List[_]]
      playerValues.listOfTechnologies should be(empty)
    }
    "be re-initializable" in {
      val playerValues: PlayerValues
      = PlayerValues(listOfBuildings = List(ResearchLab(), EnergyGrid(), Shipyard(), Hangar(), Factory(), Mine()),
        listOfUnits = List(Corvette(), Cruiser(), Destroyer(), Battleship()),
        listOfTechnologies = List(Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion()))

      playerValues.listOfUnits shouldBe a[List[_]]
      playerValues.listOfUnits should not be(empty)
      playerValues.listOfBuildings shouldBe a[List[_]]
      playerValues.listOfBuildings should not be(empty)
      playerValues.listOfTechnologies shouldBe a[List[_]]
      playerValues.listOfTechnologies should not be(empty)
    }
    "the lists should return new lists if something is appended re-initializable" in {
      val playerValues: PlayerValues = PlayerValues()

      playerValues.listOfUnits.:+(Destroyer()) should be(List[IUnit](Destroyer()))
      playerValues.listOfBuildings.:+(ResearchLab()) should be(List[IBuilding](ResearchLab()))
      playerValues.listOfTechnologies.:+(AdvancedMaterials()) should be(List[ITechnology](AdvancedMaterials()))
    }
  }
}
