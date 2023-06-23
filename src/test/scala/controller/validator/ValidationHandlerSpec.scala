package controller.validator

import controller.command.commands.*
import controller.newInterpreter.CommandTokenizer
import model.core.board.GameBoardBuilder
import model.core.board.GameBoard
import controller.validator.ValidationHandler
import controller.validator.validatorutils.*
import model.core.board.sector.impl.{PlayerSector, Sector}
import model.core.board.boardutils.Coordinate
import model.core.gameobjects.purchasable.building.Mine
import model.core.gameobjects.purchasable.units.Cruiser
import model.core.mechanics.fleets.Fleet
import model.game.gamestate.GameStateManager
import model.game.playervalues.PlayerValues
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ValidationHandlerSpec extends AnyWordSpec:
  
  "The ExpressionParser" should {
      val parser: ValidationHandler = ValidationHandler(GameStateManager(gameMap = setUpUnits()))
      
      "map empty input to EmptyCommand" in {
        val testInput: String = ""
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[EmptyCommand]
      }
      
      "map build input without param to MessageCommand" in {
        val testInput: String = "build"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map build input with invalid param to MessageCommand" in {
        val testInput: String = "build test"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map build input with valid building to BuildCommand" in {
        val testInput: String = "build mine"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[BuildCommand]
      }
      
      "map research input to without param to MessageCommand" in {
        val testInput: String = "research"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map research input to with invalid param to MessageCommand" in {
        val testInput: String = "research test"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map research input to with valid technology to ResearchCommand" in {
        val testInput: String = "research advanced materials"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[ResearchCommand]
      }
      
      "map recruit input without param to MessageCommand" in {
        val testInput: String = "recruit"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map recruit input with invalid param to MessageCommand" in {
        val testInput: String = "recruit test"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map recruit input with invalid param to RecruitCommand" in {
        val testInput: String = "recruit Cruiser"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[RecruitCommand]
      }
      
      "map show input to MessageCommand" in {
        val testInput: String = "show"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map show input with param to ShowCommand" in {
        val testInput: String = "show -overview"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[ShowCommand]
      }
      
      "map list input to ListExpression" in {
        val testInput: String = "list"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[ListCommand]
      }
      
      "map move input to MoveExpression" in {
        val testInput: String = "move x to y"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[InvalidCommand]
      }
      
      "map save input to MessageCommand" in {
        val testInput: String = "save"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map save with -f param input to SaveCommand" in {
        val testInput: String = "save -f=test"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[SaveCommand]
      }
      
      "map load input to MessageCommand" in {
        val testInput: String = "load"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map load input with -f param to " in {
        val testInput: String = "load -f=test"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[LoadCommand]
      }
      
      "map sell input to MessageCommand" in {
        val testInput: String = "sell"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map sell input with param to SellCommand" in {
        val testInput: String = "sell cruiser"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[SellCommand]
      }
      
      "map help (help / h) input to HelpExpression" in {
        val testInput: String = "help"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[HelpCommand]
      }
      
      "map exit (exit / quit) input to ExitExpression" in {
        val testInput: String = "exit"
        val testInput2: String = "quit"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[EndGameCommand]
        parser.handle(CommandTokenizer().parseInput(testInput2)).get shouldBe a[EndGameCommand]
      }
      
      "map invalid or undefined input to InvalidExpression" in {
        val testInput: String = "test"
        parser.handle(CommandTokenizer().parseInput(testInput)).get shouldBe a[InvalidCommand]
      }
      
      "map yes to UserAcceptCommand" in {
        parser.handle(CommandTokenizer().parseInput("y")).get shouldBe a[UserAcceptCommand]
        parser.handle(CommandTokenizer().parseInput("yes")).get shouldBe a[UserAcceptCommand]
      }

      "map no to UserDeclineCommand" in {
        parser.handle(CommandTokenizer().parseInput("n")).get shouldBe a[UserDeclineCommand]
        parser.handle(CommandTokenizer().parseInput("no")).get shouldBe a[UserDeclineCommand]
      }

      "map done to EndRoundExpression" in {
          parser.handle(CommandTokenizer().parseInput("done")).get shouldBe a[EndRoundCommand]
      }
  }

  private def setUpUnits(): GameBoard =
    val lSector = Sector(location = Coordinate(0, 0), 
      unitsInSector = Vector(Fleet(fleetComponents = Vector(Cruiser(), Cruiser(), Cruiser()))))
    val pSector = PlayerSector(sector = lSector)
    GameBoardBuilder().build.updateSector(pSector)
