package model.countable

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class ResearchSpec extends AnyWordSpec {

  "The ResearchOutput" when {
    "initialized" should {
      val researchOutput: Research = Research()
      "have a value of 100" in {
        researchOutput.value should be (100)
      }
    }
    "incremented by 20" should {
      val research: Research = Research()
      "return a new ResearchOutput" in {
        research.increase(Research(20)) should not be(research)
      }
      "have the expected value" in {
        research.increase(Research(20)).value should be(120)
      }
    }
    "decremented by 10" should {
      val research: Research = Research()
      "give us an Option Element" in {
        research.decrease(Research(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different research object" in {
        research.decrease(Research(10)).get should not be equal(research)
      }
      "have the value 90 when decremented by 10 after being initialized" in {
        research.decrease(Research(10)).get.value should be(90)
      }
      "return Option.empty if decreased by more than actually contained" in {
        research.decrease(Research(1001)).isEmpty should be(true)
      }
    }
  }
}
