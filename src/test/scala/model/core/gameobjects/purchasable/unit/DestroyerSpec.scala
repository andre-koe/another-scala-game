package model.core.gameobjects.purchasable.unit

import model.core.gameobjects.purchasable.units.Destroyer
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class DestroyerSpec extends AnyWordSpec {

  "A Destroyer" should {
    "have a name of Destroyer" in {
      Destroyer().name should be("Destroyer")
    }
    "have a fitting toString representation" in {
      Destroyer()
        .toString should be("Destroyer")
    }
    "return a new Destroyer object when round is decreased" in {
      Destroyer().decreaseRoundsToComplete.roundsToComplete.value should be(Destroyer().roundsToComplete.value - 1)
    }
  }

}
