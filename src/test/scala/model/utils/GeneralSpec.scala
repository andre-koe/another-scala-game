package model.utils

import model.core.board.boardutils.Coordinate
import model.core.board.sector.ISector
import model.core.board.sector.impl.{IPlayerSector, PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.building.{EnergyGrid, Factory, Hangar, Mine, ResearchLab, Shipyard}
import model.core.gameobjects.purchasable.units.*
import model.core.mechanics.fleets.Fleet
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class GeneralSpec extends AnyWordSpec with Matchers {

  val sector: ISector = Sector(location = Coordinate(0, 0),
    affiliation = Affiliation.PLAYER,
    sectorType = SectorType.BASE,
    unitsInSector = Vector(
      Fleet(name = "test",
        fleetComponents = Vector(Corvette(), Battleship()),
        location = Coordinate(0, 0),
        affiliation = Affiliation.PLAYER),
      Fleet(name = "test2",
        fleetComponents = Vector(Cruiser(), Destroyer()),
        location = Coordinate(0, 0),
        affiliation = Affiliation.PLAYER)
    ))
  val playerSector: IPlayerSector =
    PlayerSector(sector = sector,
      constQuUnits = Vector(Corvette()),
      buildingsInSector = Vector(Shipyard(), Hangar(), ResearchLab(), EnergyGrid(), Mine(), Factory()))

  "A General" when {
    "calculating generated income from playerSector" should {
      "calculate correctly" in {
        val general = new General()
        val result = general.generatedIncomeFromSector(playerSector)
        val expected =
          Vector(Shipyard(), Hangar(), ResearchLab(), EnergyGrid(), Mine(), Factory())
            .map(_.output).map(_.rHolder).reduce(_.increase(_))
        result should be(expected)
      }
    }

    "calculating generated capacity from playerSector" should {
      "calculate correctly" in {
        val general = new General()
        val result = general.generatedCapacityFromSector(playerSector)
        val expected = Hangar().output.cap
        result should be(expected)
      }
    }

    "calculating upkeep from buildings" should {
      "calculate correctly" in {
        val general = new General()
        val result = general.upkeepFromBuildings(playerSector)
        val expected =
          Vector(Shipyard(), Hangar(), ResearchLab(), EnergyGrid(), Mine(), Factory())
            .map(_.upkeep).reduce(_.increase(_))
        result should be(expected)
      }
    }

    "calculating upkeep from units in playerSector" should {
      "calculate correctly" in {
        val general = new General()
        val result = general.upkeepFromUnitsInSector(playerSector)
        val expected = Vector(Corvette(), Battleship(), Cruiser(), Destroyer()).map(_.upkeep).reduce(_.increase(_))
        result should be(expected)
      }
    }
  }
}
