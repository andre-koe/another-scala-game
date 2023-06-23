package model.core.utilities

import model.core.utilities.Capacity
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CapacitySpec extends AnyWordSpec {

  "Capacity" when {
    "initialized" should {
      val cap: Capacity = Capacity()
      "have a value of 0" in {
        cap.value should be(0)
      }
    }
    "incremented by 10" should {
      val cap: Capacity = Capacity()
      "give us a new Capacity object" in {
        cap.increase(Capacity(10)) should not be equal(cap)
      }
      "have the value 10 when incremented by 10 after being initialized" in {
        cap.increase(Capacity(10)).value should be(10)
      }
    }
    "decremented by 10 after being initialized with 20" should {
      val cap: Capacity = Capacity(20)
      "give us an Option Element" in {
        cap.decrease(Capacity(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different Capacity object" in {
        cap.decrease(Capacity(10)).get should not be equal(cap)
      }
      "have the value 10 when decremented by 10 after being initialized with 20" in {
        cap.decrease(Capacity(10)).get.value should be(10)
      }
      "return Option.empty if decreased by more than actually contained" in {
        cap.decrease(Capacity(1001)).isEmpty should be(true)
      }
    }
    "multiplied by 2 after being initiaized with 2" should {
      val cap: Capacity = Capacity(2)
      "give us a capacity with value of 4" in {
        cap.multiplyBy(2).value shouldBe 4
      }
    }
    "asked for its String representation" should {
      val cap: Capacity = Capacity(200)
      "give us a nice String" in {
        cap.toString should be(f"[Capacity: ${cap.value}]")
      }
    }
    "asked for holds" should {
      val cap1: Capacity = Capacity(10)
      "2 if capacity contains 2 times other capacity" in {
        val cap2: Capacity = Capacity(5);
        cap1.holds(cap2) should be(Some(2))
      }
      "None if capacity contains 0 times other capacity" in {
        val cap2: Capacity = Capacity(11);
        cap1.holds(cap2) should be(None)
      }
    }
  }
}

