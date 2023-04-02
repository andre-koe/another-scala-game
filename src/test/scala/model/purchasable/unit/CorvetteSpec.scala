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
        .toString should be("The Corvette is a fast and agile naval unit that excels at " +
        "harassing enemy fleets and scouting enemy positions.")
    }
  }

}
