package model.core.gameobjects.purchasable.technology

import model.core.gameobjects.purchasable.technology.NanoRobotics
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

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
