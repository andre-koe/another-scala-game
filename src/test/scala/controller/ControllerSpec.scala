package controller

import controller.command.commands._
import model.game.gamestate.GameState._
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers._

class ControllerSpec extends AnyWordSpec {

  "The Controller" should {
    val controller: Controller = Controller()
    "turn user input to List of Strings" in {
      val testInput: String = "Some String to Test"
      assert(controller.inputSanitation(testInput).isInstanceOf[List[String]])
      controller.inputSanitation(testInput) should be(List("some", "string", "to", "test"))
    }
  }

  "The mapToCommand method" should {
    val controller: Controller = Controller()
    "map empty input to EmptyCommand" in {
      val testInput: String = ""
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [EmptyCommand]
    }
    "map build input to BuildCommand" in {
      val testInput: String = "build"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [BuildCommand]
    }
    "map research input to ResearchCommand" in {
      val testInput: String = "research"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [ResearchCommand]
    }
    "map recruit input to RecruitCommand" in {
      val testInput: String = "recruit"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [RecruitCommand]
    }
    "map show input to ShowCommand" in {
      val testInput: String = "show"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [ShowCommand]
    }
    "map list input to ListCommand" in {
      val testInput: String = "list"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [ListCommand]
    }
    "map move input to MoveCommand" in {
      val testInput: String = "move x y"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [MoveCommand]
    }
    "map save input to SaveCommand" in {
      val testInput: String = "save"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [SaveCommand]
    }
    "map load input to LoadCommand" in {
      val testInput: String = "load"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [LoadCommand]
    }
    "map sell input to SellCommand" in {
      val testInput: String = "sell"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [SellCommand]
    }
    "map help (help / h) input to HelpCommand" in {
      val testInput: String = "help"
      val testInput2: String = "H"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [HelpCommand]
      controller.mapToCommand(controller.inputSanitation(testInput2), testInput2) shouldBe a [HelpCommand]
    }
    "map exit (exit / quit) input to ExitCommand" in {
      val testInput: String = "exit"
      val testInput2: String = "quit"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [EndGameCommand]
      controller.mapToCommand(controller.inputSanitation(testInput2), testInput2) shouldBe a [EndGameCommand]

    }
    "map invalid or undefined input to InvalidCommand" in {
      val testInput: String = "test"
      controller.mapToCommand(controller.inputSanitation(testInput), testInput) shouldBe a [InvalidCommand]
    }
    "map (yes / y / n / no) to UserResponseCommand" in {
      controller.mapToCommand(controller.inputSanitation("yes"), "yes") shouldBe a [UserResponseCommand]
      controller.mapToCommand(controller.inputSanitation("y"), "y") shouldBe a [UserResponseCommand]
      controller.mapToCommand(controller.inputSanitation("n"), "n") shouldBe a [UserResponseCommand]
      controller.mapToCommand(controller.inputSanitation("no"), "no") shouldBe a [UserResponseCommand]

    }
    "map done to EndRoundCommand" in {
      controller.mapToCommand(controller.inputSanitation("done"), "done") shouldBe a [EndRoundCommand]
    }
  }

  "The processInput method" should {
    "return the game state according to the input" in {
      val controller: Controller = Controller()
      controller.processInput("exit") should be (EXITED)
      controller.processInput("done") should be (END_ROUND_REQUEST)
      controller.processInput("test") should be (RUNNING)
    }
  }

  "The toString method" should {
    "return the toString representation of the GameStateManager depending on the input" in {
      val controller: Controller = Controller()
      controller.processInput("test")
      controller.toString() should be ("Unknown Input: 'test' - invalid\nEnter help to get an overview of all available commands")
    }
  }
}