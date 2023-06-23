package controller.command.commands

import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import utils.DefaultValueProvider.given_IGameValues
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.units.Corvette
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.utilities.{Capacity, ResourceHolder}
import model.game.gamestate.GameStateManager
import model.game.playervalues
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class RecruitCommandSpec extends AnyWordSpec {

  "The RecruitCommand" should {
    "invoke the correct behaviour corresponding to the request" when {

      "initialized with with valid unit and sufficient capacity and resources" in {
        val pV: PlayerValues = playervalues.PlayerValues(
          capacity = Capacity(100),
          resourceHolder = ResourceHolder(energy = Energy(1000), minerals = Minerals(1000)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)
        val sector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
        val recruitCommand = RecruitCommand(Corvette(), 2, sector, gsm)
        recruitCommand.execute().gameMap.getPlayerSectors.map(_.constQuUnits).isEmpty should be(false)
        recruitCommand.execute().toString should be("Beginning construction of 2 x Corvette " +
          s"for ${Corvette().cost.multiplyBy(2)}, completion in ${Corvette().roundsToComplete.value} rounds.")
      }

      "initialized with with valid unit and sufficient resources but insuffficient capacity" in {
        val pV: PlayerValues = playervalues.PlayerValues(
          capacity = Capacity(0),
          resourceHolder = ResourceHolder(energy = Energy(1000), minerals = Minerals(1000)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)
        val sector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
        val recruitCommand = RecruitCommand(Corvette(), 2, sector, gsm)
        recruitCommand.execute().gameMap.getSectorAtCoordinate(Coordinate(0,0)).get.unitsInSector.isEmpty should be(true)
        recruitCommand.execute().toString should be(s"Insufficient Capacity --- [Capacity: 2].")
      }

      "initialized with with valid unit and sufficient capacity but insuffficient resources" in {
        val pV: PlayerValues = playervalues.PlayerValues(
          capacity = Capacity(2),
          resourceHolder = ResourceHolder(energy = Energy(0), minerals = Minerals(0)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)
        val sector = gsm.gameMap.getSectorAtCoordinate(Coordinate(0,0)).get
        val recruitCommand = RecruitCommand(Corvette(), 2, sector, gsm)
        recruitCommand.execute().gameMap.getSectorAtCoordinate(Coordinate(0,0)).get.unitsInSector.isEmpty should be(true)
        recruitCommand.execute().toString should be(s"Insufficient Funds --- Total Lacking: [Energy: 140] [Minerals: 60].")
      }

      "initialized with with valid unit, sufficient capacity and resources but wrong sector" in {
        val pV: PlayerValues = playervalues.PlayerValues(
          capacity = Capacity(2),
          resourceHolder = ResourceHolder(energy = Energy(0), minerals = Minerals(0)))
        val gsm: GameStateManager = GameStateManager(playerValues = pV)
        val sector = gsm.gameMap.getSectorAtCoordinate(Coordinate(1, 1)).get
        val recruitCommand = RecruitCommand(Corvette(), 2, sector, gsm)
        recruitCommand.execute().gameMap.getSectorAtCoordinate(Coordinate(1, 1)).get.unitsInSector.isEmpty should be(true)
        recruitCommand.execute().toString should be(s"Can't begin construction in ${sector} - is not a player sector")
      }

    }
  }
}
