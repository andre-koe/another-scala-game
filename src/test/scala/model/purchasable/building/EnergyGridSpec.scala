package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class EnergyGridSpec extends AnyWordSpec {
  "A EnergyGrid" should {
    val hangar: EnergyGrid = EnergyGrid()
    "be named Energy Grid" in {
      hangar.name should be("Energy Grid")
    }
    "have an appropriate toString representation" in {
      hangar
        .toString should be("The Energy Grid provides a steady stream of energy to power other buildings and units.")
    }
  }

}
