package model.core.gameobjects.purchasable.technology

import model.core.gameobjects.purchasable.technology.{ITechnology, Polymer}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PolymerSpec extends AnyWordSpec {
  "Polymer" should {
    "have a name of Polymer" in {
      val polymer: ITechnology = Polymer()
      polymer.name should be("Polymer")
    }
    "have a fitting toString representation" in {
      Polymer().toString should be("Polymer")
    }
    "return a new Polymer object when round is decreased" in {
      Polymer().decreaseRoundsToComplete.roundsToComplete.value should be(Polymer().roundsToComplete.value - 1)
    }
  }
}
