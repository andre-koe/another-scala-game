package model.purchasable.technology

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class PolymerSpec extends AnyWordSpec {
  "Polymer" should {
    "have a name of Polymer" in {
      val polymer: Technology = Polymer()
      polymer.name should be("Polymer")
    }
    "have a fitting toString representation" in {
      Polymer().toString should be("Polymer")
    }
  }
}
