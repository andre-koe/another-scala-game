package model.resources.recourcetypes

import model.resources.resourcetypes.Energy
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class EnergySpec extends AnyWordSpec {
  "Energy" when {
    "initialized" should {
      val funds: Energy = Energy()
      "have a value of 0" in {
        funds.value should be(0)
      }
    }
    "incremented by 10" should {
      val funds: Energy = Energy()
      "give us a new Funds object" in {
        funds.increase(Energy(10)) should not be equal(funds)
      }
      "have the value 2 when incremented after being initialized" in {
        funds.increase(Energy(10)).value should be(10)
      }
    }
    "decremented by 10 after being initialized with 20" should {
      val funds: Energy = Energy(20)
      "give us an Option Element" in {
        funds.decrease(Energy(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different funds object" in {
        funds.decrease(Energy(10)).get should not be equal(funds)
      }
      "have the value 10 when decremented by 10 after being initialized with 20" in {
        funds.decrease(Energy(10)).get.value should be(10)
      }
      "return Option.empty if decreased by more than actually contained" in {
        funds.decrease(Energy(1001)).isEmpty should be(true)
      }
    }
    "asked for its String representation" should {
      val funds: Energy = Energy(200)
      "give us a nice String" in {
        funds.toString should be(f"[Energy: ${funds.value}]")
      }
    }
  }

}
