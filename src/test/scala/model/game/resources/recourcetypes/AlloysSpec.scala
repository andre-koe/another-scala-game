package model.game.resources.recourcetypes

import model.game.resources.resourcetypes.Alloys
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class AlloysSpec extends AnyWordSpec {

  "Alloys" when {
    "initialized" should {
      val alloys: Alloys = Alloys()
      "have a value of 0" in {
        alloys.value should be(0)
      }
    }
    "incremented by 10" should {
      val alloys: Alloys = Alloys()
      "give us a new Alloys object" in {
        alloys.increase(Alloys(10)) should not be equal(alloys)
      }
      "have the value 10 when incremented by 10 after being initialized" in {
        alloys.increase(Alloys(10)).value should be(10)
      }
    }
    "decremented by 10 after being initialized with 20" should {
      val alloys: Alloys = Alloys(20)
      "give us an Option Element" in {
        alloys.decrease(Alloys(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different alloys object" in {
        alloys.decrease(Alloys(10)).get should not be equal(alloys)
      }
      "have the value 10 when decremented by 10 after being initialized with 20" in {
        alloys.decrease(Alloys(10)).get.value should be(10)
      }
      "return Option.empty if decreased by more than actually contained" in {
        alloys.decrease(Alloys(1001)).isEmpty should be(true)
      }
    }
    "asked for its String representation" should {
      val alloys: Alloys = Alloys(200)
      "give us a nice String" in {
        alloys.toString should be(f"[Alloys: ${alloys.value}]")
      }
    }
  }
}
