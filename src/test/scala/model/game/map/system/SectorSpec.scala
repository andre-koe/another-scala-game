package model.game.map.system

import model.game.map.Coordinate
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.io.AnsiColor

class SectorSpec extends AnyWordSpec:

  "A Sector" should {

    "have be initialized by default with expected values" in {
      val sector: Sector = Sector(Coordinate())
      sector.sectorType should be(SectorType.REGULAR)
      sector.affiliation should be(Affiliation.INDEPENDENT)
    }

    "have a predefined number of buildslots depending on its type" when {

      "sector is of type Regular should be five" in {
        val secReg: Sector = Sector(Coordinate())
        secReg.sectorType should be (SectorType.REGULAR)
        secReg.buildSlots.value should be (3)
      }

      "sector is of type Base should be seven" in {
        val secReg: Sector = Sector(Coordinate(), sectorType = SectorType.BASE)
        secReg.sectorType should be(SectorType.BASE)
        secReg.buildSlots.value should be(7)
      }
    }

    "have a fitting to string representation depending on affiliation and type" when {
      "sector is of type Regular belonging to the player should be (x-x) in color blue" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.PLAYER)
        secReg.toString should be(AnsiColor.BLUE + "(0-A)" + AnsiColor.RESET)
      }

      "sector is of type Regular belonging and independent should be (x-x) in color gray" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.INDEPENDENT)
        secReg.toString should be(AnsiColor.WHITE + "(0-A)" + AnsiColor.RESET)
      }

      "sector is of type Regular belonging and belonging to the enemy should be (x-x) in color red" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.ENEMY)
        secReg.toString should be(AnsiColor.RED + "(0-A)" + AnsiColor.RESET)
      }
    }

    "be able to change affiliation" in {
      Sector(Coordinate()).changeAffiliation(Affiliation.ENEMY).affiliation should be(Affiliation.ENEMY)
    }

    "have a fitting description depending on affiliation and type" when {
      "sector is player owned and regular" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.PLAYER, sectorType = SectorType.REGULAR)
        secReg.description.contains("Regular System") should be (true)
        secReg.description.contains("Player") should be (true)
        secReg.description.contains("3") should be (true)
      }

      "sector is player owned and Base" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.PLAYER,  sectorType = SectorType.BASE)
        secReg.description.contains("Home System") should be(true)
        secReg.description.contains("Player") should be(true)
        secReg.description.contains("7") should be (true)
      }

      "sector is base and owned by enemy" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.ENEMY,  sectorType = SectorType.BASE)
        secReg.description.contains("Home System") should be(true)
        secReg.description.contains("Enemy") should be(true)
        secReg.description.contains("7") should be(true)
      }

      "sector is base and independent" in {
        val secReg: Sector = Sector(Coordinate(), affiliation = Affiliation.INDEPENDENT, sectorType = SectorType.BASE)
        secReg.description.contains("Home System") should be(true)
        secReg.description.contains("Independent") should be(true)
        secReg.description.contains("7") should be(true)
      }

    }

  }
