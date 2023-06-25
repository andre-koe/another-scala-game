package model.core.board.boardutils

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class CoordinateSpec extends AnyWordSpec {

  "A Coordinate" should {

    "be initialized with the provided values" in {
      val coordinate = Coordinate(2, 3)
      coordinate.xPos should be(2)
      coordinate.yPos should be(3)
    }

    "calculate the correct Manhattan distance to another coordinate" in {
      val coordinate1 = Coordinate(2, 3)
      val coordinate2 = Coordinate(5, 7)
      coordinate1.getDistance(coordinate2) should be(7)
    }

    "toString returns the correct string" in {
      val coordinate = Coordinate(2, 3)
      coordinate.toString should be("2-3")
    }
  }
}
