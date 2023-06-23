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
      UnitFactory("corvette",
        Sector(Coordinate(-1,-1),
          Affiliation.PLAYER,
          SectorType.REGULAR)) should be(Option(Corvette(location = Sector(Coordinate(-1,-1),
        Affiliation.PLAYER, SectorType.REGULAR))))
    }
    "create the following units based on their name" in {
      UnitFactory("battleship", Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR))
        .get should be(Battleship(location = Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR)))
      UnitFactory("corvette", Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR))
        .get should be(Corvette(location = Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR)))
      UnitFactory("cruiser", Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR))
        .get should be(Cruiser(location = Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR)))
      UnitFactory("destroyer", Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR))
        .get should be(Destroyer(location = Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR)))
    }
    "return None if an nonexitent unit name is passed to it" in {
      UnitFactory("test unit", Sector(Coordinate(-1,-1), Affiliation.PLAYER, SectorType.REGULAR)) should be(None)
    }
  }

}
