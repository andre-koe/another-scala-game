package model.core.gameobjects.purchasable.building

import model.core.gameobjects.purchasable.building.Mine
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MineSpec extends AnyWordSpec {
  "A Mine" should {
    val mine: Mine = Mine()
    "be named Mine" in {
      mine.name should be("Mine")
    }
    "have an appropriate toString representation" in {
      mine
        .toString should be("Mine")
    }
    "return a new Mine object when round is decreased" in {
      mine.decreaseRoundsToComplete.roundsToComplete.value should be(Mine().roundsToComplete.value - 1)
    }
  }

}
