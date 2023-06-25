package model.core.gameobjects.purchasable.technology

import model.core.gameobjects.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer, TechnologyFactory}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class TechnologyFactorySpec extends AnyWordSpec {

  "The TechnologyFactory" should {
    "return Options" in {
      TechnologyFactory("advanced materials") should be(Option(AdvancedMaterials()))
    }
    "create the following technologies based on their name" in {
      TechnologyFactory("advanced materials").get should be(AdvancedMaterials())
      TechnologyFactory("advanced propulsion").get should be(AdvancedPropulsion())
      TechnologyFactory("polymer").get should be(Polymer())
      TechnologyFactory("nano robotics").get should be(NanoRobotics())
    }
    "return None if an nonexitent technology name is passed to it" in {
      TechnologyFactory("test tech") should be(None)
    }
  }

}
