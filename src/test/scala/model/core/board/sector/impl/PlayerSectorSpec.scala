package model.core.board.sector.impl

import model.core.board.sector.ISector
import model.core.board.boardutils.Coordinate
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.building.{BuildingFactory, IBuilding}
import model.core.gameobjects.purchasable.units.{Corvette, UnitFactory}
import model.core.utilities.Round
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class PlayerSectorSpec extends AnyWordSpec:

  "A PlayerSector" should {

    "have affiliation as sector" in {
      val sector: ISector = Sector(Coordinate(0, 0), Affiliation.PLAYER)
      val playerSector = PlayerSector(sector)
      playerSector.affiliation should be(sector.affiliation)
    }

    "be initialized by default with expected values" in {
      val sector: ISector = Sector(Coordinate(0,0))
      val playerSector = PlayerSector(sector)
      playerSector.sector should be(sector)
      playerSector.constQuBuilding should be(Vector())
      playerSector.constQuUnits should be(Vector())
      playerSector.buildingsInSector should be(Vector())
      playerSector.unitsInSector should be(Vector())
    }

    "be able to construct a building" in {
      val sector: ISector = Sector(Coordinate(0,0))
      val building: IBuilding = BuildingFactory("Energy Grid", sector).get
      val playerSector = PlayerSector(sector)
      val newPlayerSector = playerSector.constructBuilding(building)
      newPlayerSector.constQuBuilding.head.name shouldBe building.name
    }

    "be able to construct a unit" in {
      val sector: ISector = Sector(Coordinate(0,0))
      val unit: IUnit = UnitFactory("Corvette", sector).get
      val playerSector = PlayerSector(sector)
      val newPlayerSector = playerSector.constructUnit(unit, 1)
      newPlayerSector.constQuUnits.head.name shouldBe unit.name
    }

    "be able to update the sector" in {
      val sector: ISector = Sector(Coordinate(0,0))
      val playerSector = PlayerSector(sector,
        constQuUnits = Vector(Corvette(location = sector, roundsToComplete = Round(1))))
      val updatedPlayerSector = playerSector.updateSector()
      updatedPlayerSector.unitsInSector shouldNot be(empty)
      updatedPlayerSector.constQuUnits shouldBe empty
    }

    "be able to clone itself with changes" in {
      val sector: ISector = Sector(Coordinate(0,0))
      val playerSector = PlayerSector(sector)
      val clone = playerSector.cloneWith(location = Coordinate(1,1), affiliation = Affiliation.PLAYER,
        sectorType = SectorType.BASE, unitsInSector = Vector(Fleet(location = playerSector)),
        buildSlots = playerSector.buildSlots)
      clone should not be playerSector
    }

    "be able to switch affiliation" in {
      val sector: ISector = Sector(Coordinate(0,0))
      val playerSector = PlayerSector(sector)
      val newPlayerSector = playerSector.switchAffiliation(Affiliation.ENEMY)
      newPlayerSector.affiliation should be(Affiliation.ENEMY)
    }
  }
