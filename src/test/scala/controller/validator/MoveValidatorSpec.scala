package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, MessageCommand, MoveCommand}
import controller.newInterpreter.{CommandTokenizer, InterpretedInput}
import model.core.board.GameBoardBuilder
import model.core.board.boardutils.{Coordinate, ICoordinate}
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.sector.sectorutils.{Affiliation, SectorType}
import model.core.gameobjects.purchasable.units.Corvette
import model.core.mechanics.fleets.Fleet
import model.game.gamestate.{GameStateManager, IGameStateManager}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import utils.DefaultValueProvider.given_IGameValues

class MoveValidatorSpec extends AnyWordSpec {

  "A MoveValidator" when {

    val playerSector = PlayerSector(
      sector = Sector(
        location = Coordinate(0,0),
        affiliation = Affiliation.PLAYER,
        sectorType = SectorType.BASE,
        unitsInSector = Vector(
          Fleet(
            name = "test",
            fleetComponents = Vector(Corvette(), Corvette()),
            location = Coordinate(0,0),
            affiliation = Affiliation.PLAYER))),
    )
    val gsm: IGameStateManager = GameStateManager(gameMap = GameBoardBuilder().build.updateSector(playerSector))

    "validating a move command" should {

      "return a MoveCommand if the fleet exists and the coordinate is valid" in {
        val input = "move test 0-1"
        MoveValidator(input, gsm).validate(CommandTokenizer().parseInput(input).input) should matchPattern {
          case Right(Some(_: MoveCommand)) =>
        }
      }

      "return a MessageCommand if the fleet name is 'help'" in {
        val input = "move help"
        MoveValidator(input, gsm).validate(CommandTokenizer().parseInput(input).input) should matchPattern {
          case Right(Some(_: MessageCommand)) =>
        }
      }

      "return an InvalidCommand if the fleet doesn't exist" in {
        val input = "move snack 0-1"
        MoveValidator(input, gsm).validate(CommandTokenizer().parseInput(input).input) should matchPattern {
          case Right(Some(_: InvalidCommand)) =>
        }
      }

      "return an InvalidCommand if the sector is invalid" in {
        val input = "move test 10-10"
        MoveValidator(input, gsm).validate(CommandTokenizer().parseInput(input).input) should matchPattern {
          case Right(Some(_: InvalidCommand)) =>
        }
      }
    }
  }
}
