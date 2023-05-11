package model.game.purchasable.building

import model.game.purchasable.building.ResearchLab
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class ResearchLabSpec extends AnyWordSpec {
  "A ResearchLab" should {
    val researchLab: ResearchLab = ResearchLab()
    "be named Research Lab" in {
      researchLab.name should be("Research Lab")
    }
    "have an appropriate toString representation" in {
      researchLab
        .toString should be("Research Lab")
    }
    "return a new ResearchLab object when round is decreased" in {
      researchLab.decreaseRoundsToComplete.roundsToComplete.value should be(ResearchLab().roundsToComplete.value - 1)
    }
  }

}
