package model.game.purchasable.technology

import model.game.purchasable.technology.{AdvancedMaterials, AdvancedPropulsion, NanoRobotics, Polymer, TechnologyFactory}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class TechnologyFactorySpec extends AnyWordSpec {

  "The TechnologyFactory" should {
    val technologyFactory: TechnologyFactory = TechnologyFactory()
    "return Options" in {
      technologyFactory.create("advanced materials") should be(Option(AdvancedMaterials()))
    }
    "create the following technologies based on their name" in {
      technologyFactory.create("advanced materials").get should be(AdvancedMaterials())
      technologyFactory.create("advanced propulsion").get should be(AdvancedPropulsion())
      technologyFactory.create("polymer").get should be(Polymer())
      technologyFactory.create("nano robotics").get should be(NanoRobotics())
    }
    "return None if an nonexitent technology name is passed to it" in {
      technologyFactory.create("test tech") should be(None)
    }
  }

}
