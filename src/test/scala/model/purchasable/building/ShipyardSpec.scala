package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ShipyardSpec extends AnyWordSpec {
  "A Shipyard" should {
    val shipyard: Shipyard = Shipyard()
    "be named Shipyard" in {
      shipyard.name should be("Shipyard")
    }
    "have an appropriate toString representation" in {
      shipyard
        .toString should be("The Shipyard allows players to construct and upgrade naval units for their fleet.")
    }
  }

}
