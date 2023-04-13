package model.resources.recourcetypes

import model.resources.resourcetypes.ResearchPoints
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class ResearchPointsSpec extends AnyWordSpec {

  "The ResearchOutput" when {
    "initialized" should {
      val researchOutput: ResearchPoints = ResearchPoints()
      "have a value of 0" in {
        researchOutput.value should be (0)
      }
    }
    "incremented by 20" should {
      val research: ResearchPoints = ResearchPoints()
      "return a new ResearchOutput" in {
        research.increase(ResearchPoints(20)) should not be(research)
      }
      "have the expected value" in {
        research.increase(ResearchPoints(20)).value should be(20)
      }
    }
    "decremented by 10 after being initialized with 20" should {
      val research: ResearchPoints = ResearchPoints(20)
      "give us an Option Element" in {
        research.decrease(ResearchPoints(10)).isDefined should be(true)
      }
      "give us an Option Element containing a different research object" in {
        research.decrease(ResearchPoints(10)).get should not be equal(research)
      }
      "have the value 10 when decremented by 10 after being initialized with 20" in {
        research.decrease(ResearchPoints(10)).get.value should be(10)
      }
      "return Option.empty if decreased by more than actually contained" in {
        research.decrease(ResearchPoints(1001)).isEmpty should be(true)
      }
    }
    "asked for its String representation" should {
      val research: ResearchPoints = ResearchPoints(200)
      "give us a nice String" in {
        research.toString should be(f"[Research Points: ${research.value}]")
      }
    }
  }
}
