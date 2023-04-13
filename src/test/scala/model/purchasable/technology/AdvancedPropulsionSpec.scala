package model.purchasable.technology

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class AdvancedPropulsionSpec extends AnyWordSpec {
  "AdvancedPropulsion" should {
    "have a name of Advanced Propulsion" in {
      AdvancedPropulsion().name should be("Advanced Propulsion")
    }
    "have a fitting toString representation" in {
      AdvancedPropulsion().toString should be("Advanced Propulsion")
    }
    "return a new AdvancedPropulsion object when round is decreased" in {
      AdvancedPropulsion().decreaseRoundsToComplete
        .roundsToComplete.value should be(AdvancedPropulsion().roundsToComplete.value - 1)
    }
  }
}
