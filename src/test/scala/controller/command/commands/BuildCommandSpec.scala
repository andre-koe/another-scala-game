package controller.command.commands

import model.core.board.sector.ISector
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.building.Mine
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import model.core.utilities.ResourceHolder
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class BuildCommandSpec extends AnyWordSpec {

  "The BuildCommand" should {

    val sector: ISector = Sector(Coordinate(-1,-1), Affiliation.INDEPENDENT, SectorType.REGULAR)

    "correctly handle the construction of a Building if sufficient funds are available" in {
      val playerValues: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          energy = Energy(1000), 
          minerals = Minerals(1000),
          alloys = Alloys(1000)
        ))
      val gameStateManager: GameStateManager = GameStateManager(playerValues = playerValues)
      val gsm = BuildCommand(Mine(), sector, gameStateManager).execute()
      
      gsm.gameMap.getPlayerSectors.flatMap(_.constQuBuilding) should not be empty
      gsm.toString should be(s"Beginning construction of ${Mine().name} " +
        s"for ${Mine().cost}, completion in ${Mine().roundsToComplete.value} rounds.")
    }

    "return a GameState with insufficientFunds Message if player lacks necessary funds for building" in {
      val playerValues: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          energy = Energy(),
          minerals = Minerals(),
          alloys = Alloys()
        ))
      val gameStateManager: GameStateManager = GameStateManager(playerValues = playerValues)
      val gsm = BuildCommand(Mine(), sector, gameStateManager).execute()

      gsm.gameMap.getPlayerSectors.flatMap(_.constQuBuilding).isEmpty should be(true)
      gsm.toString should be( s"Insufficient Funds --- " +
        s"${gameStateManager.playerValues.resourceHolder.lacking(Mine().cost)}.")
    }
  }
}
