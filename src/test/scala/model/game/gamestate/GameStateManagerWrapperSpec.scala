package model.game.gamestate

import model.core.board.boardutils.GameBoardInfoWrapper
import model.core.gameobjects.purchasable.technology.Polymer
import model.game.playervalues.PlayerValues
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameStateManagerWrapperSpec extends AnyWordSpec with Matchers {

  "A GameStateManagerWrapper" should {

    val gsm = GameStateManager()
    val gsmWrapper = GameStateManagerWrapper(gsm)

    "correctly get current round" in {
      gsmWrapper.getCurrentRound shouldBe gsm.round.value
    }

    "correctly get capacity" in {
      gsmWrapper.getCapacity shouldBe gsm.playerValues.capacity.value
    }

    "correctly get player technologies" in {
      val pVal = PlayerValues(listOfTechnologies = Vector(Polymer()))
      val gsmWithTech = GameStateManager(playerValues = pVal)
      val gsmWrapperWithTech = GameStateManagerWrapper(gsmWithTech)
      gsmWrapperWithTech.getPlayerTech should be(Vector(Polymer()))
    }

    "correctly get technologies currently being researched by the player" in {
      val pVal = PlayerValues(listOfTechnologiesCurrentlyResearched = Vector(Polymer()))
      val gsmWithTech = GameStateManager(playerValues = pVal)
      val gsmWrapperWithTech = GameStateManagerWrapper(gsmWithTech)
      gsmWrapperWithTech.getPlayerTechCurrentlyResearch should be(Vector(Polymer()))
    }

    "correctly get player resources" in {
      gsmWrapper.getPlayerResources shouldBe(gsm.playerValues.resourceHolder.resourcesAsVector)
    }

    "correctly get player income" in {
      gsmWrapper.getPlayerIncome shouldBe(gsm.playerValues.income.resourcesAsVector)
    }

    "correctly get player upkeep" in {
      gsmWrapper.getPlayerUpkeep shouldBe(gsm.playerValues.upkeep.resourcesAsVector)
    }

    "correctly get player net income" in {
      gsmWrapper.getPlayerIncome shouldBe(gsm.playerValues.income.resourcesAsVector)
    }

    "correctly get game map info" in {
      gsmWrapper.getGameMapInfo shouldBe(GameBoardInfoWrapper(gsm.gameMap))
    }

    "correctly get game state" in {
      gsmWrapper.getState shouldBe(gsm.gameState)
    }

    "correctly get researchable technologies" in {
      gsmWrapper.getResearchableTech shouldBe(gsm.gameValues.tech)
    }

    "correctly get constructable buildings" in {
      gsmWrapper.getConstructableBuildings shouldBe(gsm.gameValues.buildings)
    }

    "correctly get recruitable units" in {
      gsmWrapper.getRecruitableUnits shouldBe(gsm.gameValues.units)
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
