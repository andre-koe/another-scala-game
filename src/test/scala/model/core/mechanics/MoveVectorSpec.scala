package model.core.mechanics

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import model.core.board.sector.impl.Sector
import model.core.board.boardutils.Coordinate

class MoveVectorSpec extends AnyWordSpec with Matchers {

  "A MoveVector" when {
    "valid" should {
      "calculate the correct distance between start and target" in {
        val startSector = Coordinate(0,0)
        val targetSector = Coordinate(3,4)
        val moveVector = MoveVector(start = startSector, target = targetSector)

        moveVector.getDistance should be(Some(7)) // Manhattan distance between (0,0) and (3,4) is 7
      }
    }

    "invalid" should {
      "return None when calculating the distance" in {
        val invalidSector = Coordinate(-1,-1)
        val moveVector = MoveVector(start = invalidSector, target = invalidSector)

        moveVector.getDistance should be(None)
      }
    }

    "updating start or target" should {
      "correctly update the sectors" in {
        val initialStart = Coordinate(0,0)
        val initialTarget = Coordinate(3,4)
        val newStart = Coordinate(1,1)
        val newTarget = Coordinate(4,5)

        var moveVector = MoveVector(start = initialStart, target = initialTarget)

        moveVector = moveVector.setStart(newStart)
        moveVector.start should be(newStart)

        moveVector = moveVector.setTarget(newTarget)
        moveVector.target should be(newTarget)
      }
    }
  }
}
