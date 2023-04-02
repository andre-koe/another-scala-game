package model.game

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class CoordinateSpec extends AnyWordSpec {

  "A Coordinate" should {
    "by default be initialized with 0,0" in {
      val coordinate: Coordinate = Coordinate()
      coordinate.posX should be(0)
      coordinate.posY should be(0)
    }
    "have a toString representation depending on it's initialization Values" in {
      val coordinate00: Coordinate = Coordinate()
      val coordinate01: Coordinate = Coordinate(1,3)
      val coordinate02: Coordinate = Coordinate(2,6)

      coordinate00.toString should be("[0 - 0]")
      coordinate01.toString should be("[1 - 3]")
      coordinate02.toString should be("[2 - 6]")
    }
  }

}
