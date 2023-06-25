package controller.command.commands

import model.core.gameobjects.purchasable.technology.AdvancedMaterials
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import model.core.utilities.ResourceHolder
import utils.DefaultValueProvider.given_IGameValues
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ResearchCommandSpec extends AnyWordSpec {
  "The ResearchCommand" should {

    "initiated the research of a unresearched technology if playerfunds are sufficient" in {
      val gsm: GameStateManager = GameStateManager(playerValues = Vector(PlayerValues(resourceHolder =
        ResourceHolder(researchPoints = ResearchPoints(1000)))))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().currentPlayerValues.listOfTechnologiesCurrentlyResearched.isEmpty should be(false)
      researchCommand.execute().toString should be(s"Beginning research of 'Advanced Materials' " +
          s"for ${AdvancedMaterials().cost}, completion in ${AdvancedMaterials().roundsToComplete.value} rounds.")
    }

    "return correct failure message if technology has already been researched" in {
      val gsm: GameStateManager = GameStateManager(playerValues = Vector(PlayerValues(
        resourceHolder = ResourceHolder(researchPoints = ResearchPoints(1000)),
        listOfTechnologies = Vector(AdvancedMaterials()))
      ))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().currentPlayerValues.listOfTechnologiesCurrentlyResearched.isEmpty should be(true)
      researchCommand.execute().toString should be(s"'${AdvancedMaterials().toString}' is either being currently " +
        s"researched or has already been researched.")
    }

    "return correct failure message if technology is already currently being researched" in {
      val gsm: GameStateManager = GameStateManager(playerValues = Vector(PlayerValues(
        resourceHolder = ResourceHolder(researchPoints = ResearchPoints(1000)),
        listOfTechnologiesCurrentlyResearched = Vector(AdvancedMaterials()))
      ))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().currentPlayerValues.listOfTechnologiesCurrentlyResearched.length should be(1)
      researchCommand.execute().toString should be(s"'${AdvancedMaterials().toString}' is either being currently " +
        s"researched or has already been researched.")
    }

    "return correct failure message if player has insufficient funds" in {
      val gsm: GameStateManager = GameStateManager(playerValues = Vector(PlayerValues(resourceHolder =
        ResourceHolder(researchPoints = ResearchPoints(10)))))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().currentPlayerValues.listOfTechnologiesCurrentlyResearched.isEmpty should be(true)
      researchCommand.execute().toString should be(s"Insufficient Funds --- " +
        s"${gsm.currentPlayerValues.resourceHolder.lacking(AdvancedMaterials().cost)}.")
    }
  }
}
