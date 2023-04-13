package model.purchasable.unit

import model.purchasable.units.Cruiser
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

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
