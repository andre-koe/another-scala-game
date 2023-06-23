package controller.command.memento

import controller.command.commands.{BuildCommand, EndRoundCommand}
import model.core.board.boardutils.Coordinate
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.gameobjects.purchasable.building.BuildingFactory
import model.game.gamestate.GameStateManager
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import utils.DefaultValueProvider.given_IGameValues

class UndoRedoManagerSpec extends AnyWordSpec with Matchers {

  "An UndoRedoManager" should {

    "add memento correctly and execute the command" in {
      val manager = UndoRedoManager()
      val gameStateManager = GameStateManager()
      val command = BuildCommand(BuildingFactory("energy grid", Sector(Coordinate(0,0))).get
        , PlayerSector(Sector(Coordinate(0,0))), gameStateManager)

      val result = manager.addMemento(command)
      result shouldBe command.execute()
    }

    "undo correctly and retrieve the previous game state" in {
      val manager = UndoRedoManager()
      val gameStateManager = GameStateManager()
      val command = BuildCommand(BuildingFactory("energy grid", Sector(Coordinate(0,0))).get
        , PlayerSector(Sector(Coordinate(0, 0))), gameStateManager)

      manager.addMemento(command)
      manager.undo shouldBe Some(gameStateManager)
    }

    "redo correctly and retrieve the previous game state" in {
      val manager = UndoRedoManager()
      val gameStateManager = GameStateManager()
      val command = BuildCommand(BuildingFactory("energy grid", Sector(Coordinate(0,0))).get
        , PlayerSector(Sector(Coordinate(0, 0))), gameStateManager)

      manager.addMemento(command)
      manager.undo
      manager.redo shouldBe Some(command.execute())
    }

    "undo should return None if nothing to undo" in {
      val manager = UndoRedoManager()
      manager.undo shouldBe None
    }

    "redo should return None if nothing to redo" in {
      val manager = UndoRedoManager()
      manager.redo shouldBe None
    }
  }
}