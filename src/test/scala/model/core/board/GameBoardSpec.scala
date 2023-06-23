package model.core.board

import model.core.board.sector.ISector
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import org.scalatest.OptionValues

class GameBoardSpec extends AnyWordSpec with Matchers with OptionValues {

  "A GameBoard" when {
    val sizeX = 10
    val sizeY = 10
    val emptyBoardData: Vector[Vector[ISector]] = Vector.fill(sizeX, sizeY)(Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR))
    val gameBoard = GameBoard(sizeX, sizeY, emptyBoardData)

    "getting a sector at a valid coordinate" should {
      val coordinate = Coordinate(5,5)

      "return the sector" in {
        gameBoard.getSectorAtCoordinate(coordinate).value shouldBe a [Sector]
      }
    }

    "getting a sector at an invalid coordinate" should {
      val coordinate = Coordinate(sizeX+1, sizeY+1)

      "return None" in {
        gameBoard.getSectorAtCoordinate(coordinate) shouldBe None
      }
    }

    "checking if a sector exists" should {
      "return true for valid coordinates" in {
        gameBoard.sectorExists(Coordinate(5,5)) shouldBe true
      }

      "return false for invalid coordinates" in {
        gameBoard.sectorExists(Coordinate(sizeX+1, sizeY+1)) shouldBe false
      }
    }

    "changing a sector to PlayerSector" should {
      val coordinate = Coordinate(5,5)

      "change the sector correctly" in {
        val updatedBoard = gameBoard.toPlayerSector(coordinate, Fleet())
        updatedBoard.getSectorAtCoordinate(coordinate).value shouldBe a [PlayerSector]
      }
    }

    "updating a sector" should {
      val newSector = Sector(Coordinate(5,5), Affiliation.PLAYER, SectorType.BASE)

      "update the sector correctly" in {
        val updatedBoard = gameBoard.updateSector(newSector)
        updatedBoard.getSectorAtCoordinate(newSector.location).value shouldBe newSector
      }
    }
  }
}
