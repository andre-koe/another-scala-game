package model.core.gameobjects.purchasable.building

import model.core.gameobjects.purchasable.building.EnergyGrid
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor

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
