package model.game.purchasable.technology

import model.game.purchasable.technology.NanoRobotics
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class NanoRoboticsSpec extends AnyWordSpec {
  "NanoRobotics" should {
    "have a name of Nano Robotics" in {
      NanoRobotics().name should be("Nano Robotics")
    }
    "have a fitting toString representation" in {
      NanoRobotics().toString should be("Nano Robotics")
    }
    "return a new NanoRobotics object when round is decreased" in {
      NanoRobotics().decreaseRoundsToComplete
        .roundsToComplete.value should be(NanoRobotics().roundsToComplete.value - 1)
    }
  }
}
