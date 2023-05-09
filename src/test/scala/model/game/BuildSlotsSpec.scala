package model.game

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class BuildSlotsSpec extends AnyWordSpec {

  "BuildSlots" should {

    "be initialized with 0 by default" in {
      BuildSlots().value should be(0)
    }

    "be increaseable" in {
      BuildSlots().increase(BuildSlots(5)).value should be(5)
    }

    "be decreaseable" when {
      "remaining buildslots are > 0" in {
        BuildSlots(1).decrease(BuildSlots(1)).isDefined should be(true)
        BuildSlots(1).decrease(BuildSlots(1)).get.value should be(0)
      }
    }

    "not be decreaseable" when {
      "remaining buildslots are <= 0" in {
        BuildSlots().decrease(BuildSlots(1)).isDefined should be(false)
      }
    }

    "be decrementable" when {
      "remaining buildslots are > 0" in {
        BuildSlots(1).decrement.isDefined should be(true)
        BuildSlots(1).decrement.get.value should be(0)
      }
    }

    "not be decrementable" when {
      "remaining buildslots are <= 0" in {
        BuildSlots().decrement.isDefined should be(false)
      }
    }

    "have the following to string" in {
      BuildSlots(3).toString should be("[Build Slots: 3]")
    }
  }

}
