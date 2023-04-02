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
        .toString should be("The Cruiser is a versatile naval unit that can engage both " +
        "ground and air targets with its array of weapons and sensors.")
    }
  }

}
