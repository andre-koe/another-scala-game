package model.core.gameobjects.purchasable.building

import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.building.{BuildingFactory, EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class BuildingFactorySpec extends AnyWordSpec {

  "The BuildingFactory" should {
    val location: Sector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)
    "return Options" in {
      BuildingFactory("shipyard", location) should be(Option(Shipyard()))
    }
    "create the following buildings based on their name" in {
      BuildingFactory("shipyard", location).get should be(Shipyard())
      BuildingFactory("hangar", location).get should be(Hangar())
      BuildingFactory("research lab", location).get should be(ResearchLab())
      BuildingFactory("energy grid", location).get should be(EnergyGrid())
      BuildingFactory("mine", location).get should be(Mine())
      BuildingFactory("factory", location).get should be(Factory())
    }
    "return None if an nonexitent building name is passed to it" in {
      BuildingFactory("test", location) should be(None)
    }
  }

}
