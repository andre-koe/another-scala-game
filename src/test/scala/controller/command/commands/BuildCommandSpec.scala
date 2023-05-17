package controller.command.commands

import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import model.game.purchasable.building.Mine
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class BuildCommandSpec extends AnyWordSpec {

  "The BuildCommand" should {

    "correctly handle the construction of a Building if sufficient funds are available" in {
      val playerValues: PlayerValues = PlayerValues(
        resourceHolder = ResourceHolder(
          energy = Energy(1000), 
          minerals = Minerals(1000),
          alloys = Alloys(1000)
        ))
      val gameStateManager: GameStateManager = GameStateManager(playerValues = playerValues)
      val gsm = BuildCommand(Mine(), gameStateManager).execute()
      
      gsm.playerValues.listOfBuildingsUnderConstruction should not be empty
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
      val gsm = BuildCommand(Mine(), gameStateManager).execute()

      gsm.playerValues.listOfBuildingsUnderConstruction.isEmpty should be(true)
      gsm.toString should be( s"Insufficient Funds --- " +
        s"${gameStateManager.playerValues.resourceHolder.lacking(Mine().cost)}.")
    }
  }
}
