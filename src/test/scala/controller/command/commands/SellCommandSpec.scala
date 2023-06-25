package controller.command.commands

import model.core.board
import model.core.board.sector.impl.{PlayerSector, Sector}
import utils.DefaultValueProvider.given_IGameValues
import model.core.board.boardutils.Coordinate
import model.core.board.sector.ISector
import model.core.board.sector.sectorutils.Affiliation
import model.core.board.{GameBoard, GameBoardBuilder}
import model.core.gameobjects.purchasable.IGameObject
import model.core.gameobjects.purchasable.building.{EnergyGrid, IBuilding, Mine}
import model.core.gameobjects.purchasable.units.Cruiser
import model.core.mechanics.fleets.Fleet
import model.core.mechanics.fleets.components.units.IUnit
import model.core.utilities.{Capacity, ResourceHolder}
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec


class SellCommandSpec extends AnyWordSpec {
  
  "The SellCommand" should {
    "sell a unit if player owns it" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(unitsToSell =
        Vector(Fleet(fleetComponents = Vector(Cruiser(), Cruiser()))))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
      val sellCommand: SellCommand = SellCommand(Cruiser(), 1, where = sector, gsm)
      sellCommand.execute().gameMap.getPlayerSectors(Affiliation.PLAYER).flatMap(_.unitsInSector).size  should be(1)
      sellCommand.execute().toString should be(s"Successfully Sold: 1 x Cruiser for a " +
        s"profit of ${Cruiser().cost.divideBy(2)}.")
    }

    "sell multiple units if player owns them" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(unitsToSell =
        Vector(Fleet(fleetComponents = Vector(Cruiser(), Cruiser()))))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
      val sellCommand: SellCommand = SellCommand(Cruiser(), 2, where = sector, gsm)
      sellCommand.execute().gameMap.getPlayerSectors(Affiliation.PLAYER).flatMap(_.unitsInSector).size  should be(0)
      sellCommand.execute().toString should be(s"Successfully Sold: 2 x Cruiser for a " +
        s"profit of ${Cruiser().cost}.")
    }

    "sell multiple units of multiple fleets if player owns them" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(unitsToSell =
        Vector(Fleet(fleetComponents = Vector(Cruiser(), Cruiser())), Fleet(fleetComponents = Vector(Cruiser(), Cruiser()))))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0, 0)).get
      val sellCommand: SellCommand = SellCommand(Cruiser(), 3, where = sector, gsm)
      sellCommand.execute().gameMap.getPlayerSectors(Affiliation.PLAYER).flatMap(_.unitsInSector).size should be(1)
      sellCommand.execute().toString should be(s"Successfully Sold: 3 x Cruiser for a " +
        s"profit of ${Cruiser().cost.multiplyBy(3).divideBy(2)}.")
    }

    "sell a building if player owns it" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(Vector(Mine()))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
      val sellCommand: SellCommand = SellCommand(Mine(), 1, where = sector, gsm)
      sellCommand.execute().gameMap.getPlayerSectors(Affiliation.PLAYER).flatMap(_.buildingsInSector).size should be(0)
      sellCommand.execute().toString should be(s"Successfully Sold: 1 x Mine for a " +
        s"profit of ${Mine().cost.divideBy(2)}.")
    }

    "sell multiple buildings if player owns them" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(Vector(Mine(), Mine()))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
      val sellCommand: SellCommand = SellCommand(Mine(), 2, where = sector, gsm)
      sellCommand.execute().gameMap.getPlayerSectors(Affiliation.PLAYER).flatMap(_.buildingsInSector).size should be(0)
      sellCommand.execute().toString should be(s"Successfully Sold: 2 x Mine for a " +
        s"profit of ${Mine().cost}.")
    }

    "display an error if specified location not a player sector owns them" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(Vector(Mine(), Mine()))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(1, 0)).get
      val sellCommand: SellCommand = SellCommand(Mine(), 2, where = sector, gsm)
      sellCommand.execute().toString should be("Specified sector is not a PlayerSector - invalid\nEnter help to get an overview of all available commands")
    }

    "display an error if specified object cannot be sold does not exist" in {
      val gsm: GameStateManager = getGSMWithThingsToSell(Vector(Mine(), Mine()))
      val sector: ISector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0, 0)).get
      val sellCommand: SellCommand = SellCommand(Fleet(name = "test"), 2, where = sector, gsm)
      sellCommand.execute().toString should be("Cannot sell test - invalid\nEnter help to get an overview of all available commands")
    }

  }

  private def getGSMWithThingsToSell(buildingsToSell: Vector[IBuilding] = Vector(),
                                     unitsToSell: Vector[Fleet] = Vector()): GameStateManager =
    val sector = Sector(location = Coordinate(0, 0), unitsInSector = unitsToSell)
    val pSector = PlayerSector(sector = sector, buildingsInSector = buildingsToSell)
    val gameBoard = GameBoardBuilder().build.updateSector(pSector)
    GameStateManager(gameMap = gameBoard)
}
