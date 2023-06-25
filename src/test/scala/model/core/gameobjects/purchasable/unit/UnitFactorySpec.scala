package model.core.gameobjects.purchasable.unit

import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.units.*
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
class UnitFactorySpec extends AnyWordSpec {

  "The UnitFactory" should {
    "return Options" in {
      UnitFactory("corvette") should be(Option(Corvette()))
    }
    "create the following units based on their name" in {
      UnitFactory("battleship").get should be(Battleship())
      UnitFactory("corvette").get should be(Corvette())
      UnitFactory("cruiser").get should be(Cruiser())
      UnitFactory("destroyer").get should be(Destroyer())
    }
    "return None if an nonexitent unit name is passed to it" in {
      UnitFactory("test unit") should be(None)
    }
  }

}
