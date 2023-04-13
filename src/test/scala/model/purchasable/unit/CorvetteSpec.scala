package model.purchasable.unit

import model.purchasable.units.Corvette
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

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
