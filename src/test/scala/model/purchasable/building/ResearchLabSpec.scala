package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ResearchLabSpec extends AnyWordSpec {
  "A ResearchLab" should {
    val researchLab: ResearchLab = ResearchLab()
    "be named Research Lab" in {
      researchLab.name should be("Research Lab")
    }
    "have an appropriate toString representation" in {
      researchLab
        .toString should be("The Research Lab increases research output and unlocks new technologies for players.")
    }
  }

}
