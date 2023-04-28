package model.game

import model.game.purchasable.building.{EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.game.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer}
import model.game.purchasable.units.{Battleship, Corvette, Cruiser, Destroyer}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class GameValuesSpec extends AnyWordSpec {
  "The GameValues" should {
    "be initialized correctly by default" in {
      val gameValues: GameValues = GameValues()
      gameValues.listOfUnits shouldBe a [List[_]]
      gameValues.listOfUnits should not be(empty)
      gameValues.listOfUnits should be(List(Corvette(), Cruiser(), Destroyer(), Battleship()))

      gameValues.listOfBuildings shouldBe a [List[_]]
      gameValues.listOfBuildings should not be(empty)
      gameValues.listOfBuildings should be(List(ResearchLab(), EnergyGrid(), Shipyard(), Hangar(), Factory(), Mine()))

      gameValues.listOfTechnologies shouldBe a [List[_]]
      gameValues.listOfTechnologies should not be(empty)
      gameValues
        .listOfTechnologies should be(List(Polymer(), AdvancedMaterials(), NanoRobotics(), AdvancedPropulsion()))
    }
    "be re-initializable" in {
      val gameValues: GameValues = GameValues(listOfBuildings = List(), listOfUnits = List(), listOfTechnologies = List())

      gameValues.listOfUnits shouldBe a[List[_]]
      gameValues.listOfUnits should be(empty)

      gameValues.listOfBuildings shouldBe a[List[_]]
      gameValues.listOfBuildings should be(empty)

      gameValues.listOfTechnologies shouldBe a[List[_]]
      gameValues.listOfTechnologies should be(empty)
    }
  }
}
