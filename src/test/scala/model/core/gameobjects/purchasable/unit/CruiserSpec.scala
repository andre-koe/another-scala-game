package model.core.gameobjects.purchasable.unit

import model.core.gameobjects.purchasable.units.Cruiser
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CruiserSpec extends AnyWordSpec {

  "A Cruiser" should {
    "have a name of Cruiser" in {
      Cruiser().name should be("Cruiser")
    }
    "have a fitting toString representation" in {
      Cruiser()
        .toString should be("Cruiser")
    }
    "return a new Cruiser object when round is decreased" in {
      Cruiser().decreaseRoundsToComplete.roundsToComplete.value should be(Cruiser().roundsToComplete.value - 1)
    }
  }

}
