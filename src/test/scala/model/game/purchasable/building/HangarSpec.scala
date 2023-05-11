package model.game.purchasable.building

import model.game.purchasable.building.Hangar
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class HangarSpec extends AnyWordSpec {
  "A Hangar" should {
    val hangar: Hangar = Hangar()
    "be named Hangar" in {
      hangar.name should be("Hangar")
    }
    "have an appropriate toString representation" in {
      hangar
        .toString should be("Hangar")
    }
    "return a new Hangar object when round is decreased" in {
      hangar.decreaseRoundsToComplete.roundsToComplete.value should be(Hangar().roundsToComplete.value - 1)
    }
  }

}
