package controller.command.commands

import model.game.PlayerValues
import model.game.gamestate.GameStateManager
import model.game.purchasable.technology.AdvancedMaterials
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals, ResearchPoints}
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ResearchCommandSpec extends AnyWordSpec {
  "The ResearchCommand" should {

    "initiated the research of a unresearched technology if playerfunds are sufficient" in {
      val gsm: GameStateManager = GameStateManager(playerValues = PlayerValues(resourceHolder =
          ResourceHolder(researchPoints = ResearchPoints(1000))))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().playerValues.listOfTechnologiesCurrentlyResearched.isEmpty should be(false)
      researchCommand.execute().toString should be(s"Beginning research of 'Advanced Materials' " +
          s"for ${AdvancedMaterials().cost}, completion in ${AdvancedMaterials().roundsToComplete.value} rounds.")
    }

    "return correct failure message if technology has already been researched" in {
      val gsm: GameStateManager = GameStateManager(playerValues = PlayerValues(
        resourceHolder = ResourceHolder(researchPoints = ResearchPoints(1000)),
        listOfTechnologies = List(AdvancedMaterials())
      ))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().playerValues.listOfTechnologiesCurrentlyResearched.isEmpty should be(true)
      researchCommand.execute().toString should be(s"'${AdvancedMaterials().toString}' is either being currently " +
        s"researched or has already been researched.")
    }

    "return correct failure message if technology is already currently being researched" in {
      val gsm: GameStateManager = GameStateManager(playerValues = PlayerValues(
        resourceHolder = ResourceHolder(researchPoints = ResearchPoints(1000)),
        listOfTechnologiesCurrentlyResearched = List(AdvancedMaterials())
      ))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().playerValues.listOfTechnologiesCurrentlyResearched.length should be(1)
      researchCommand.execute().toString should be(s"'${AdvancedMaterials().toString}' is either being currently " +
        s"researched or has already been researched.")
    }

    "return correct failure message if player has insufficient funds" in {
      val gsm: GameStateManager = GameStateManager(playerValues = PlayerValues(resourceHolder =
        ResourceHolder(researchPoints = ResearchPoints(10))))

      val researchCommand = ResearchCommand(AdvancedMaterials(), gsm)
      researchCommand.execute().playerValues.listOfTechnologiesCurrentlyResearched.isEmpty should be(true)
      researchCommand.execute().toString should be(s"Insufficient Funds --- " +
        s"${gsm.playerValues.resourceHolder.lacking(AdvancedMaterials().cost)}.")
    }
  }
}
