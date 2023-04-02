package model.purchasable.building

import org.scalatest.matchers.should.Matchers._
import org.scalatest.wordspec.AnyWordSpec
class BuildingFactorySpec extends AnyWordSpec {

  "The BuildingFactory" should {
    val buildingFactory: BuildingFactory = BuildingFactory()
    "return Options" in {
      buildingFactory.create("shipyard") should be(Option(Shipyard()))
    }
    "create the following buildings based on their name" in {
      buildingFactory.create("shipyard").get should be(Shipyard())
      buildingFactory.create("hangar").get should be(Hangar())
      buildingFactory.create("research lab").get should be(ResearchLab())
      buildingFactory.create("energy grid").get should be(EnergyGrid())
      buildingFactory.create("mine").get should be(Mine())
      buildingFactory.create("factory").get should be(Factory())
    }
    "return None if an nonexitent building name is passed to it" in {
      buildingFactory.create("test") should be(None)
    }
  }

}
