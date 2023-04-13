package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class EnergyGridSpec extends AnyWordSpec {
  "A EnergyGrid" should {
    val energyGrid: EnergyGrid = EnergyGrid()
    "be named Energy Grid" in {
      energyGrid.name should be("Energy Grid")
    }
    "have an appropriate toString representation" in {
      energyGrid
        .toString should be("Energy Grid")
    }
    "return a new EnergyGrid object when round is decreased" in {
      energyGrid.decreaseRoundsToComplete.roundsToComplete.value should be(EnergyGrid().roundsToComplete.value - 1)
    }
  }

}
