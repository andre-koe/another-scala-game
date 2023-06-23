package model.game.gamestate.gamestates

import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.boardutils.Coordinate
import model.core.board.{GameBoard, GameBoardBuilder}
import model.core.gameobjects.purchasable.building.Mine
import model.core.gameobjects.purchasable.technology.Polymer
import model.core.gameobjects.purchasable.units.Corvette
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.mechanics.fleets.Fleet
import model.core.utilities.{ResourceHolder, Round}
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class EndRoundConfirmationStateSpec extends AnyWordSpec {

  "The EndRoundConfirmationState" should {
    "End the current round and update all necesserary values of the current gamestate and return a new one" in {

      val pv = PlayerValues(
        listOfTechnologiesCurrentlyResearched = Vector(Polymer()),
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(100))
      )

      val gsm = GameStateManager(round = Round(), playerValues = pv, gameMap = setUpBuildingsAndUnits())

      EndRoundConfirmationState().update(gsm).round.value should be(2)
      EndRoundConfirmationState().update(gsm)
        .playerValues.listOfTechnologiesCurrentlyResearched.map(_.roundsToComplete.value).max should be(2)
      EndRoundConfirmationState().update(gsm).playerValues.resourceHolder.minerals.value should be(120)
      EndRoundConfirmationState().update(gsm).playerValues.resourceHolder.energy.value < 100 should be(true)
    }
  }

  private def setUpBuildingsAndUnits(): GameBoard =
    val lSector = Sector(
      location = Coordinate(0, 0),
      unitsInSector = Vector(Fleet(fleetComponents = Vector(Corvette(), Corvette()))))
    val pSector = PlayerSector(sector = lSector, buildingsInSector = Vector(Mine(), Mine(), Mine()))
    GameBoardBuilder().build.updateSector(pSector)

}
