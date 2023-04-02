package model.purchasable.building

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class MineSpec extends AnyWordSpec {
  "A Mine" should {
    val mine: Mine = Mine()
    "be named Mine" in {
      mine.name should be("Mine")
    }
    "have an appropriate toString representation" in {
      mine
        .toString should be("The Mine extracts minerals which are used to produce alloys.")
    }
  }

}
