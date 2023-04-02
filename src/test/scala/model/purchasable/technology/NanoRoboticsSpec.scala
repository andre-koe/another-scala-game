package model.purchasable.technology

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class NanoRoboticsSpec extends AnyWordSpec {
  "NanoRobotics" should {
    "have a name of Nano Robotics" in {
      NanoRobotics().name should be("Nano Robotics")
    }
    "have a fitting toString representation" in {
      NanoRobotics().toString should be("Nano Robotics")
    }
  }
}
