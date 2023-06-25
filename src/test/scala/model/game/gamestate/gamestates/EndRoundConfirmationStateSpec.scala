package model.game.gamestate.gamestates

import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.boardutils.Coordinate
import model.core.board.sector.sectorutils.Affiliation
import utils.DefaultValueProvider.given_IGameValues
import model.core.board.{GameBoardBuilder, IGameBoard}
import model.core.gameobjects.purchasable.building.Mine
import model.core.gameobjects.purchasable.technology.Polymer
import model.core.gameobjects.purchasable.units.{Corvette, Cruiser}
import model.core.gameobjects.resources.resourcetypes.{Alloys, Energy, Minerals}
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.Fleet
import model.core.utilities.{ResourceHolder, Round}
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class EndRoundConfirmationStateSpec extends AnyWordSpec {

  "The EndRoundConfirmationState" should {

    "End the current round and update all necesserary values of the current gamestate and return a new one" in {

      val pv = Vector(PlayerValues(
        affiliation = Affiliation.PLAYER,
        listOfTechnologiesCurrentlyResearched = Vector(Polymer()),
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(100))
      ))

      val gsm = GameStateManager(round = Round(), playerValues = pv, gameMap = setUpBuildingsAndUnits())

      EndRoundConfirmationState().update(gsm).round.value should be(2)
      EndRoundConfirmationState().update(gsm)
        .currentPlayerValues.listOfTechnologiesCurrentlyResearched.map(_.roundsToComplete.value).max should be(2)
      EndRoundConfirmationState().update(gsm).currentPlayerValues.resourceHolder.minerals.value should be(130)
      EndRoundConfirmationState().update(gsm).currentPlayerValues.resourceHolder.energy.value < 100 should be(true)
    }


    "Add sector to player sectors after peaceful takeover" in {

      val pv = Vector(PlayerValues(
        affiliation = Affiliation.PLAYER,
        listOfTechnologiesCurrentlyResearched = Vector(Polymer()),
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(100))
      ))

      val gsm = GameStateManager(round = Round(), playerValues = pv, gameMap = setUpUnitsWithMovement())

      EndRoundConfirmationState().update(gsm).round.value should be(2)
      EndRoundConfirmationState().update(gsm).gameMap.getPlayerSectors(Affiliation.PLAYER).length should be(2)
      EndRoundConfirmationState().update(gsm).gameMap.getSectorAtCoordinate(Coordinate(0, 0)).get.unitsInSector.isEmpty should be(true)
      EndRoundConfirmationState().update(gsm).gameMap.getSectorAtCoordinate(Coordinate(0, 1)).get.unitsInSector.isEmpty should be(false)
    }


    "Add sector to player sectors after battle won" in {

      val pv = Vector(PlayerValues(
        affiliation = Affiliation.PLAYER,
        listOfTechnologiesCurrentlyResearched = Vector(Polymer()),
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(100))
      ))

      val gsm = GameStateManager(round = Round(), playerValues = pv, gameMap = setUpBuildingsAndUnits())
    }

    "remove fleet after battle lost" in {

      val pv = Vector(PlayerValues(
        affiliation = Affiliation.PLAYER,
        listOfTechnologiesCurrentlyResearched = Vector(Polymer()),
        resourceHolder = ResourceHolder(energy = Energy(100), minerals = Minerals(100), alloys = Alloys(100))
      ))

      val gsm = GameStateManager(round = Round(), playerValues = pv, gameMap = setUpUnitsWithMovementAndEnemies())

      EndRoundConfirmationState().update(gsm).round.value should be(2)
      EndRoundConfirmationState().update(gsm).gameMap.getPlayerSectors(Affiliation.PLAYER).length should be(1)
      EndRoundConfirmationState().update(gsm).gameMap.getSectorAtCoordinate(Coordinate(0, 0)).get.unitsInSector.isEmpty should be(true)
      EndRoundConfirmationState().update(gsm).gameMap.getSectorAtCoordinate(Coordinate(0, 1)).get.unitsInSector.isEmpty should be(false)
      EndRoundConfirmationState().update(gsm).gameMap.getSectorAtCoordinate(Coordinate(0, 1)).get.affiliation should be(Affiliation.ENEMY)
    }
  }

  private def setUpBuildingsAndUnits(): IGameBoard =
    val lSector = Sector(
      location = Coordinate(0, 0),
      unitsInSector = Vector(Fleet(fleetComponents = Vector(Corvette(), Corvette()))))
    val pSector = PlayerSector(sector = lSector, buildingsInSector = Vector(Mine(), Mine(), Mine()))
    GameBoardBuilder().build.updateSector(pSector)

  private def setUpUnitsWithMovement(): IGameBoard =
    val coord = Coordinate(0, 0)
    val lSector = Sector(
      location = coord,
      unitsInSector = Vector(
        Fleet(
          location = coord,
          affiliation = Affiliation.PLAYER,
          moveVector = MoveVector(coord, Coordinate(0,1)),
          fleetComponents = Vector(Corvette(), Corvette()))))
    val pSector = PlayerSector(sector = lSector)
    GameBoardBuilder().build.updateSector(pSector)


  private def setUpUnitsWithMovementAndEnemies(): IGameBoard =
    val coord = Coordinate(0, 0)
    val coord2 = Coordinate(0,1)
    val lSector = Sector(
      location = coord,
      affiliation = Affiliation.PLAYER,
      unitsInSector = Vector(
        Fleet(
          location = coord,
          affiliation = Affiliation.PLAYER,
          moveVector = MoveVector(coord, Coordinate(0, 1)),
          fleetComponents = Vector(Corvette(), Corvette()))))
    val rSector = Sector(
      location = coord2,
      affiliation = Affiliation.ENEMY,
      unitsInSector = Vector(
        Fleet(
          location = coord2,
          affiliation = Affiliation.ENEMY,
          moveVector = MoveVector(coord2, coord2),
          fleetComponents = Vector(Cruiser()))))
    val plSector = PlayerSector(sector = lSector)
    val prSector = PlayerSector(sector = rSector)
    val board = GameBoardBuilder().build.updateSector(plSector)
    board.updateSector(prSector)

}
