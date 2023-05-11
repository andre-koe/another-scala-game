package model.game.purchasable.unit

import model.game.purchasable.units.Destroyer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

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
