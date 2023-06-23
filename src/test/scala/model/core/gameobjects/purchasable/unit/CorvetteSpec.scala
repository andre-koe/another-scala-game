package model.core.gameobjects.purchasable.unit

import model.core.gameobjects.purchasable.units.Corvette
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CorvetteSpec extends AnyWordSpec {
  
  "A Corvette" should {
    "have a name of Corvette" in {
      Corvette().name should be("Corvette")
    }
    "have a fitting toString representation" in {
      Corvette()
        .toString should be("Corvette")
    }
    "return a new Corvette object when round is decreased" in {
      Corvette().decreaseRoundsToComplete.roundsToComplete.value should be(Corvette().roundsToComplete.value - 1)
    }
  }

}
