package model.core.board.boardutils

import model.core.board.GameBoard
import model.core.board.logic.GameBoardData
import model.core.board.sector.ISector
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.building.{EnergyGrid, Mine}
import model.core.gameobjects.purchasable.units.{Battleship, Corvette}
import model.core.mechanics.fleets.Fleet
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

import scala.swing.event.Key

class GameBoardInfoWrapperSpec extends AnyWordSpec with Matchers {
  "A GameBoardInfoWrapper" when {
    "wrapping a GameBoard" should {
      val gameBoard = GameBoard(7, 7, GameBoardData(7, 7).returnGameBoardData)
      val wrapper = GameBoardInfoWrapper(gameBoard)

      "retrieve GameBoard size" in {
        wrapper.getSizeX shouldEqual 7
        wrapper.getSizeY shouldEqual 7
      }

      "retrieve all GameBoard data" in {
        wrapper.getData shouldEqual gameBoard.getSectors
      }

      "retrieve a sector at a valid position" in {
        wrapper.getSector(3, 3) shouldBe defined
      }

      "not retrieve a sector at an invalid position" in {
        wrapper.getSector(8, 8) should not be defined
      }

      "retrieve player sector count" in {
        wrapper.getPlayerSectorCount shouldEqual gameBoard.getPlayerSectors.length
      }

      "retrieve free build slots" in {
        wrapper.getFreeBuildSlots shouldEqual
          gameBoard.getPlayerSectors
            .map(x => x.buildSlots.value - (x.constQuBuilding.length + x.buildingsInSector.length)).head
      }

      "retrieve used capacity" in {
        wrapper.getUsedCapacity shouldEqual 0 // Default start cap
      }

      "retrieve free build slots in sector" in {
        wrapper.getFreeBuildSlotsInSectorTotal(gameBoard.data(0)(0)) shouldEqual 10
        wrapper.getFreeBuildSlotsInSectorTotal(gameBoard.data(0)(1)) shouldEqual 5
      }

      "retrieve remaining build slots in sector" in {
        val sector: ISector = Sector(Coordinate(0,0), sectorType = SectorType.BASE)
        val sector2: ISector = Sector(Coordinate(0,0), sectorType = SectorType.REGULAR)
        val pSector: ISector =
          PlayerSector(sector = sector,
            constQuBuilding = Vector(Mine(location = sector)),
            buildingsInSector = Vector(EnergyGrid(location = sector)))
        val pSector2: ISector =
          PlayerSector(sector = sector2,
            constQuBuilding = Vector(Mine(location = sector)),
            buildingsInSector = Vector(EnergyGrid(location = sector)))
        gameBoard.updateSector(pSector)
        gameBoard.updateSector(pSector2)
        wrapper.getFreeBuildSlotsInSectorRemaining(pSector).get shouldEqual 6
        wrapper.getFreeBuildSlotsInSectorRemaining(pSector2).get shouldEqual 1
      }

      "retrieve buildings in sector" in {
        val sector: ISector = Sector(Coordinate(0,0))
        val pSector: ISector = PlayerSector(sector = sector, buildingsInSector = Vector(EnergyGrid(location = sector)))
        wrapper.update(pSector).getBuildingsInSector(pSector).head.name shouldEqual "Energy Grid"
      }

      "retrieve buildings under construction in sector" in {
        val sector: ISector = Sector(Coordinate(0, 0))
        val pSector: ISector = PlayerSector(sector = sector, constQuBuilding = Vector(EnergyGrid(location = sector)))
        gameBoard.updateSector(pSector)
        wrapper.getBuildingConstructionInSector(pSector).head.name shouldEqual "Energy Grid"
      }

      "retrieve fleets in sector" in {
        val sector: ISector = Sector(location = Coordinate(0, 0),
          unitsInSector = Vector(Fleet(fleetComponents = Vector(Corvette()))))
        val pSector: ISector = PlayerSector(sector = sector)
        wrapper.update(pSector).getFleetsInSector(pSector).head.name.startsWith("Battlegroup") shouldBe true
      }

      "retrieve units in sector" in {
        val sector: ISector = Sector(location = Coordinate(0, 0),
          unitsInSector = Vector(Fleet(fleetComponents = Vector(Corvette()))))
        val pSector: ISector = PlayerSector(sector = sector)
        wrapper.update(pSector).getUnitsInSector(pSector).head.name shouldBe ("Corvette")
      }

      "retrieve units under construction in all sectors" in {
        val sector: ISector = Sector(Coordinate(0, 0))
        val sector2: ISector = Sector(Coordinate(1, 1))
        val pSector: ISector = PlayerSector(sector = sector, constQuUnits = Vector(Corvette(location = sector)))
        val pSector2: ISector = PlayerSector(sector = sector2, constQuUnits = Vector(Battleship(location = sector)))

        wrapper.update(pSector).update(pSector2)
          .getUnitConstructionInSectors.length shouldEqual 2
        wrapper.update(pSector).update(pSector2)
          .getUnitConstructionInSectors.map(_.name).contains("Battleship") shouldBe true
        wrapper.update(pSector).update(pSector2)
          .getUnitConstructionInSectors.map(_.name).contains("Battleship") shouldBe true
      }

      "retrieve buildings under construction in all sectors" in {
        val sector: ISector = Sector(Coordinate(0, 0), affiliation = Affiliation.PLAYER)
        val sector2: ISector = Sector(Coordinate(1, 1), affiliation = Affiliation.PLAYER)
        val pSector: ISector = PlayerSector(sector = sector, constQuBuilding = Vector(EnergyGrid(location = sector)))
        val pSector2: ISector = PlayerSector(sector = sector2, constQuBuilding = Vector(Mine(location = sector)))

        wrapper.update(pSector)
          .update(pSector2).getBuildingConstructionInSectors.length shouldEqual 2
        wrapper.update(pSector)
          .update(pSector2).getBuildingConstructionInSectors.map(_.name).contains("Mine") shouldBe true
        wrapper.update(pSector)
          .update(pSector2).getBuildingConstructionInSectors.map(_.name).contains("Energy Grid") shouldBe true
      }

      "retrieve gameboard size x" in {
        wrapper.getSizeX shouldBe gameBoard.sizeX
      }

      "retrieve gameboard size y" in {
        wrapper.getSizeY shouldBe gameBoard.sizeY
      }
    }
  }
}
