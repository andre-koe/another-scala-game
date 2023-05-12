package controller.validator

import controller.command.commands.*
import controller.newInterpreter.ExpressionParser
import controller.validator.ValidationHandler
import controller.validator.utils.*
import model.game.PlayerValues
import model.game.gamestate.GameStateManager
import model.game.purchasable.building.Mine
import model.game.purchasable.units.Cruiser
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class ValidationHandlerSpec extends AnyWordSpec:
  
  "The ExpressionParser" should {
    val playerValues = PlayerValues(listOfUnits = List(Cruiser(), Cruiser(), Cruiser()))
      val parser: ValidationHandler = ValidationHandler(GameStateManager(playerValues = playerValues))
      
      "map empty input to EmptyCommand" in {
        val testInput: String = ""
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[EmptyCommand]
      }
      
      "map build input without param to MessageCommand" in {
        val testInput: String = "build"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map build input with invalid param to MessageCommand" in {
        val testInput: String = "build test"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map build input with valid building to BuildCommand" in {
        val testInput: String = "build mine"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[BuildCommand]
      }
      
      "map research input to without param to MessageCommand" in {
        val testInput: String = "research"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map research input to with invalid param to MessageCommand" in {
        val testInput: String = "research test"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map research input to with valid technology to ResearchCommand" in {
        val testInput: String = "research advanced materials"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[ResearchCommand]
      }
      
      "map recruit input without param to MessageCommand" in {
        val testInput: String = "recruit"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map recruit input with invalid param to MessageCommand" in {
        val testInput: String = "recruit test"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map recruit input with invalid param to RecruitCommand" in {
        val testInput: String = "recruit Cruiser"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[RecruitCommand]
      }
      
      "map show input to MessageCommand" in {
        val testInput: String = "show"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map show input with param to ShowCommand" in {
        val testInput: String = "show -overview"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[ShowCommand]
      }
      
      "map list input to ListExpression" in {
        val testInput: String = "list"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[ListCommand]
      }
      
      "map move input to MoveExpression" in {
        val testInput: String = "move x to y"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[InvalidCommand]
      }
      
      "map save input to MessageCommand" in {
        val testInput: String = "save"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map save with -f param input to SaveCommand" in {
        val testInput: String = "save -f=test"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[SaveCommand]
      }
      
      "map load input to MessageCommand" in {
        val testInput: String = "load"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map load input with -f param to " in {
        val testInput: String = "load -f=test"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[LoadCommand]
      }
      
      "map sell input to MessageCommand" in {
        val testInput: String = "sell"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[MessageCommand]
      }

      "map sell input with param to SellCommand" in {
        val testInput: String = "sell cruiser"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[SellCommand]
      }
      
      "map help (help / h) input to HelpExpression" in {
        val testInput: String = "help"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[HelpCommand]
      }
      
      "map exit (exit / quit) input to ExitExpression" in {
        val testInput: String = "exit"
        val testInput2: String = "quit"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[EndGameCommand]
        parser.handle(ExpressionParser().parseInput(testInput2)).get shouldBe a[EndGameCommand]
      }
      
      "map invalid or undefined input to InvalidExpression" in {
        val testInput: String = "test"
        parser.handle(ExpressionParser().parseInput(testInput)).get shouldBe a[InvalidCommand]
      }
      
      "map yes to UserAcceptCommand" in {
        parser.handle(ExpressionParser().parseInput("y")).get shouldBe a[UserAcceptCommand]
        parser.handle(ExpressionParser().parseInput("yes")).get shouldBe a[UserAcceptCommand]
      }

      "map no to UserDeclineCommand" in {
        parser.handle(ExpressionParser().parseInput("n")).get shouldBe a[UserDeclineCommand]
        parser.handle(ExpressionParser().parseInput("no")).get shouldBe a[UserDeclineCommand]
      }

      "map done to EndRoundExpression" in {
          parser.handle(ExpressionParser().parseInput("done")).get shouldBe a[EndRoundCommand]
      }
  }
