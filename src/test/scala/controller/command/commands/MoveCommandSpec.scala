package controller.command.commands

import model.core.board.GameBoardBuilder
import model.core.board.boardutils.Coordinate
import model.core.board.sector.impl.Sector
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.units.Corvette
import model.core.mechanics.MoveVector
import model.core.mechanics.fleets.Fleet
import model.game.gamestate.GameStateManager
import utils.DefaultValueProvider.given_IGameValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class MoveCommandSpec extends AnyWordSpec {

  "The MoveCommand" should {
    "Return an informational String if fleet doesnt exist" in {
      val gameStateManager: GameStateManager = GameStateManager()
      val moveCommand: MoveCommand = MoveCommand("something", Coordinate(1,1), gameStateManager)
      moveCommand.execute().toString should be("The specified fleet or sector doesn't exist")
    }

    "Return a new gsm instance with updated fleet MoveVector if fleet exist" in {

      val lSector = Sector(location = Coordinate(0,0),
        affiliation = Affiliation.PLAYER,
        sectorType = SectorType.BASE,
        unitsInSector = Vector(
          Fleet(name = "test",
            fleetComponents = Vector(Corvette()), location = Coordinate(0,0),
            moveVector = MoveVector(), affiliation = Affiliation.PLAYER)))

      
      val gameStateManager: GameStateManager = GameStateManager(gameMap = GameBoardBuilder().build.updateSector(lSector))
      val moveCommand: MoveCommand = MoveCommand("test", Coordinate(1, 1), gameStateManager)
      moveCommand.execute().gameMap
        .data(0)(0).unitsInSector.head.moveVector.start should be(Coordinate(0,0))
      moveCommand.execute().gameMap
        .data(0)(0).unitsInSector.head.moveVector.target should be(Coordinate(1,1))
    }
  }

}
