package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class HangarSpec extends AnyWordSpec {
  "A Hangar" should {
    val hangar: Hangar = Hangar()
    "be named Hangar" in {
      hangar.name should be("Hangar")
    }
    "have an appropriate toString representation" in {
      hangar
        .toString should be("The Hangar provides additional capacity " +
        "for air units and allows players to repair and upgrade them.")
    }
  }

}
