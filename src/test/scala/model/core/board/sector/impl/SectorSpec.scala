package model.core.board.sector.impl

import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.units.Corvette
import model.core.mechanics.fleets.Fleet
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

import scala.io.AnsiColor

class SectorSpec extends AnyWordSpec {

  "A Sector" should {

    "be initialized with the provided values" in {
      val sector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.BASE, Vector.empty)
      sector.location should be(Coordinate(0,0))
      sector.affiliation should be(Affiliation.PLAYER)
      sector.sectorType should be(SectorType.BASE)
      sector.unitsInSector should be(Vector.empty)
    }

    "return correct build slots based on the sector type" in {
      val baseSector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.BASE, Vector.empty)
      baseSector.buildSlots.value should be(10)

      val regularSector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.REGULAR, Vector.empty)
      regularSector.buildSlots.value should be(5)
    }

    "switch affiliation correctly" in {
      val initialSector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.BASE, Vector.empty)
      val newSector = initialSector.switchAffiliation(Affiliation.ENEMY)

      newSector.affiliation should be(Affiliation.ENEMY)
    }

    "clone with new values correctly" in {
      val initialSector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.BASE, Vector.empty)
      val newUnits: Vector[Fleet] =
        Vector(Fleet(location = initialSector.location, fleetComponents = Vector(Corvette())))
      val clonedSector = initialSector.cloneWith(unitsInSector = newUnits)

      clonedSector.unitsInSector should be(newUnits)
    }

    "toString returns the correct string for affiliation Player" in {
      val sector = Sector(Coordinate(0,0), Affiliation.PLAYER, SectorType.BASE, Vector.empty)
      sector.toString should be(AnsiColor.BLUE + "[0-0]" + AnsiColor.RESET)
    }

    "toString returns the correct string for affiliation Enemy" in {
      val sector = Sector(Coordinate(0, 0), Affiliation.ENEMY, SectorType.BASE, Vector.empty)
      sector.toString should be(AnsiColor.RED + "[0-0]" + AnsiColor.RESET)
    }

    "toString returns the correct string for affiliation Independent" in {
      val sector = Sector(Coordinate(0, 0), Affiliation.INDEPENDENT, SectorType.BASE, Vector.empty)
      sector.toString should be(AnsiColor.WHITE + "[0-0]" + AnsiColor.RESET)
    }
  }
}
