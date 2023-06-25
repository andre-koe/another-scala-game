package model.game.gamestate

import model.core.board.boardutils.GameBoardInfoWrapper
import utils.DefaultValueProvider.given_IGameValues
import model.core.gameobjects.purchasable.technology.Polymer
import model.core.utilities.GameValues
import model.game.playervalues.PlayerValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameStateManagerWrapperSpec extends AnyWordSpec with Matchers {

  "A GameStateManagerWrapper" should {

    val gameValues: GameValues = GameValues()
    val gsm = GameStateManager()
    val gsmWrapper = GameStateManagerWrapper(gsm)

    "correctly get current round" in {
      gsmWrapper.getCurrentRound shouldBe gsm.round.value
    }

    "correctly get capacity" in {
      gsmWrapper.getCapacity shouldBe gsm.currentPlayerValues.capacity.value
    }

    "correctly get player technologies" in {
      val pVal = Vector(PlayerValues(listOfTechnologies = Vector(Polymer())), PlayerValues(listOfTechnologies = Vector()))
      val gsmWithTech = GameStateManager(playerValues = pVal)
      val gsmWrapperWithTech = GameStateManagerWrapper(gsmWithTech)
      gsmWrapperWithTech.getPlayerTech should be(Vector(Polymer()))
    }

    "correctly get technologies currently being researched by the player" in {
      val pVal = Vector(PlayerValues(listOfTechnologiesCurrentlyResearched = Vector(Polymer())))
      val gsmWithTech = GameStateManager(playerValues = pVal)
      val gsmWrapperWithTech = GameStateManagerWrapper(gsmWithTech)
      gsmWrapperWithTech.getPlayerTechCurrentlyResearch should be(Vector(Polymer()))
    }

    "correctly get player resources" in {
      gsmWrapper.getPlayerResources shouldBe(gsm.currentPlayerValues.resourceHolder.resourcesAsVector)
    }

    "correctly get player income" in {
      gsmWrapper.getPlayerIncome shouldBe(gsm.currentPlayerValues.income.resourcesAsVector)
    }

    "correctly get player upkeep" in {
      gsmWrapper.getPlayerUpkeep shouldBe(gsm.currentPlayerValues.upkeep.resourcesAsVector)
    }

    "correctly get player net income" in {
      gsmWrapper.getPlayerIncome shouldBe(gsm.currentPlayerValues.income.resourcesAsVector)
    }

    "correctly get game map info" in {
      gsmWrapper.getGameMapInfo shouldBe(GameBoardInfoWrapper(gsm.gameMap))
    }

    "correctly get game state" in {
      gsmWrapper.getState shouldBe(gsm.gameState)
    }

    "correctly get researchable technologies" in {
      gsmWrapper.getResearchableTech should be(gameValues.tech)
    }

    "correctly get constructable buildings" in {
      gsmWrapper.getConstructableBuildings should be(gameValues.buildings)
    }

    "correctly get recruitable units" in {
      gsmWrapper.getRecruitableUnits should be(gameValues.units)
    }

    "correctly get game map size in x direction" in {
      gsmWrapper.getGameMapSizeX shouldBe(gsm.gameMap.sizeX)
    }

    "correctly get game map size in y direction" in {
      gsmWrapper.getGameMapSizeY shouldBe(gsm.gameMap.sizeY)
    }

    "correctly get GameStateManager" in {
      gsmWrapper.getGSM shouldBe(gsm)
    }
  }
}
