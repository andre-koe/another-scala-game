package model.core.gameobjects.resources

import model.core.gameobjects.resources.resourcetypes.Minerals
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MineralsSpec extends AnyWordSpec {

  "Minerals" when {
    "initialized" should {
      val minerals: Minerals = Minerals()
      "have a value of 0" in {
        minerals.value should be(0)
      }
    }
    "incremented by 10" should {
      val minerals: Minerals = Minerals()
      "give us a new Minerals object" in {
        minerals.increase(Minerals(10)) should not be equal(minerals)
      }
      "have the value 10 when incremented by 10 after being initialized" in {
        minerals.increase(Minerals(10)).value should be(10)
      }
    }
    "decremented by 10 after being initialized with 20" should {
      val minerals: Minerals = Minerals(20)
      "give us an Option Element" in {
        minerals.decrease(Minerals(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different minerals object" in {
        minerals.decrease(Minerals(10)).get should not be equal(minerals)
      }
      "have the value 10 when decremented by 10 after being initialized with 20" in {
        minerals.decrease(Minerals(10)).get.value should be(10)
      }
      "return Option.empty if decreased by more than actually contained" in {
        minerals.decrease(Minerals(1001)).isEmpty should be(true)
      }
    }
    "asked for its String representation" should {
      val minerals: Minerals = Minerals(200)
      "give us a nice String" in {
        minerals.toString should be(f"[Minerals: ${minerals.value}]")
      }
    }
  }
}
