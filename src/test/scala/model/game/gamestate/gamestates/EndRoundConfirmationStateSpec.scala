package model.game.gamestate.gamestates

import model.game.Round
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import model.game.purchasable.building.Mine
import model.game.purchasable.technology.Polymer
import model.game.purchasable.units.Corvette
import model.game.resources.ResourceHolder
import model.game.resources.resourcetypes.{Alloys, Energy, Minerals}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

class EndRoundConfirmationStateSpec extends AnyWordSpec {

  "The EndRoundConfirmationState" should {
    "End the current round and update all necesserary values of the current gamestate and return a new one" in {
      val pv = PlayerValues(
        listOfUnits = List(Corvette(), Corvette()),
        listOfBuildings = List(Mine(), Mine()),
        listOfTechnologiesCurrentlyResearched = List(Polymer()),
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(100))
      )

      val gsm = GameStateManager(playerValues = pv, round = Round())

      EndRoundConfirmationState().update(gsm).round.value should be(2)
      EndRoundConfirmationState().update(gsm)
        .playerValues.listOfTechnologiesCurrentlyResearched.map(_.roundsToComplete.value).max should be(2)
      EndRoundConfirmationState().update(gsm).playerValues.resourceHolder.minerals.value should be(120)
      EndRoundConfirmationState().update(gsm).playerValues.resourceHolder.energy.value < 100 should be(true)
    }
  }

}
