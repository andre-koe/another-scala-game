package model.core.board.logic

import model.core.board.sector.ISector
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameBoardDataSpec extends AnyWordSpec with Matchers {

  "A GameBoardData" when {
    "generating game board data" should {
      val cols = 10
      val rows = 10
      val gameBoardData = GameBoardData(cols, rows)
      val gameBoard = gameBoardData.returnGameBoardData

      "have the correct size" in {
        gameBoard should have length cols
        gameBoard.foreach(_.should(have.length(rows)))
      }

      "have a PlayerSector at first coordinate" in {
        gameBoard(0)(0) shouldBe a [PlayerSector]
      }

      "have an EnemySector at last coordinate" in {
        val lastCoordinateSector = gameBoard(cols - 1)(rows - 1)
        lastCoordinateSector shouldBe a [Sector]
        lastCoordinateSector.asInstanceOf[Sector].affiliation should be (Affiliation.ENEMY)
        lastCoordinateSector.asInstanceOf[Sector].sectorType should be (SectorType.BASE)
      }

      "have IndependentSectors at all other coordinates" in {
        for {
          col <- gameBoard.drop(1).dropRight(1)
          sector <- col.drop(1).dropRight(1)
        } {
          sector shouldBe a [Sector]
          sector.asInstanceOf[Sector].affiliation should be (Affiliation.INDEPENDENT)
          sector.asInstanceOf[Sector].sectorType should be (SectorType.REGULAR)
        }
      }
    }
  }
}
