package controller

import controller.playeractions.ActionType._
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

  "The mapToAction method" should {
    val controller: Controller = Controller()
    "map empty input to EmptyAction" in {
      val testInput: String = ""
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(EMPTY)
    }
    "map valid build input with name to Build(Option(name))" in {
      val testInput: String = "Build test"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(BUILD(Option("test")))
    }
    "map invalid build input (no argument provided) to INVALID" in {
      val testInput: String = "Build testHouse shrimp"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"build: build requires exactly 1 argument <building name>\nprovided: '$testInput'")))
    }
    "map invalid build input (too many arguments provided) to INVALID" in {
      val testInput: String = "Build testHouse shrimp"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"build: build requires exactly 1 argument <building name>\nprovided: '$testInput'")))
    }
    "map valid research input with name to RESEARCH(Option(name))" in {
      val testInput: String = "research test"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(RESEARCH(Option("test")))
    }
    "map invalid research input (no argument provided) to RESEARCH(None)" in {
      val testInput: String = "research"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"research: research requires exactly 1 argument <technology name>\nprovided: '$testInput'")))
    }
    "map invalid research input (too many arguments provided) to INVALID" in {
      val testInput: String = "research test shrimp"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"research: research requires exactly 1 argument <technology name>\nprovided: '$testInput'")))
    }
    "map valid recruit input with name to RECRUIT(Option(name), None)" in {
      val testInput: String = "recruit test"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(RECRUIT(Option("test"), None))
    }
    "map valid recruit input with name and quantity to RECRUIT(Option(name), Option(quantity))" in {
      val testInput: String = "recruit test 10"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(RECRUIT(Option("test"), Option("10")))
    }
    "map invalid recruit input (no argument provided) to INVALID" in {
      val testInput: String = "recruit"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"recruit: recruit requires exactly 1 argument <unit name> and takes 1 optional parameter " +
          s"<quantity> if quantity is omitted the default of 1 will be used\nprovided: '$testInput'")))
    }
    "map invalid recruit input (too many arguments provided) to INVALID" in {
      val testInput: String = "recruit test 10 hi"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"recruit: recruit requires exactly 1 argument <unit name> and takes 1 optional parameter " +
          s"<quantity> if quantity is omitted the default of 1 will be used\nprovided: '$testInput'")))
    }
    "map valid move input with name to MOVE" in {
      val testInput: String = "move x y"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(MOVE(Option("x"), Option("y")))
    }
    "map invalid move input to INVALID (too many position args are provided)" in {
      val testInput: String = "move x y z"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"move: move requires exactly 2 positional arguments <x> <y>\nprovided: '$testInput'")))
    }
    "map invalid move input to INVALID (one position arg is provided)" in {
      val testInput: String = "move x"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"move: move requires exactly 2 positional arguments <x> <y>\nprovided: '$testInput'")))
    }
    "map invalid move input to INVALID (no position args are provided)" in {
      val testInput: String = "move"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"move: move requires exactly 2 positional arguments <x> <y>\nprovided: '$testInput'")))
    }
    "map valid save input without name to SAVE(None)" in {
      val testInput: String = "save"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SAVE(None))
    }
    "map valid save input with name to SAVE(Option(name))" in {
      val testInput: String = "save x"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SAVE(Option("x")))
    }
    "map invalid save input to INVALID" in {
      val testInput: String = "save x y"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"save: save can have exactly 1 optional parameter <filename>\nprovided: '$testInput'")))
    }
    "map valid load input without name to LOAD(None)" in {
      val testInput: String = "load"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(LOAD(None))
    }
    "map valid load input with name to LOAD(Option(name))" in {
      val testInput: String = "load x"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(LOAD(Option("x")))
    }
    "map invalid load input to INVALID" in {
      val testInput: String = "load x y"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"load: load can have exactly 1 optional parameter <filename>\nprovided: '$testInput'")))
    }
    "map valid sell input to SELL(Option(name))" in {
      val testInput: String = "sell x"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SELL(Option("x")))
    }
    "map invalid sell input to INVALID" in {
      val testInput: String = "sell"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(s"sell: sell has exactly 1 parameter <name>\nprovided: '$testInput'")))
    }
    "map invalid show input to INVALID" in {
      val testInput: String = "show"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(
          Some(s"show: show has exactly 1 parameter <overview | buildings | units | technology>\nprovided: '$testInput'")))
    }
    "map show overview input to SHOW(Option(overview))" in {
      val testInput: String = "show overview"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SHOW(Option("overview")))
    }
    "map show buildings input to SHOW(Option(buildings))" in {
      val testInput: String = "show buildings"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SHOW(Option("buildings")))
    }
    "map show tech input to SHOW(Option(technology))" in {
      val testInput: String = "show technology"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SHOW(Option("technology")))
    }
    "map show units input to SHOW(Option(units))" in {
      val testInput: String = "show units"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(SHOW(Option("units")))
    }
    "map invalid list input to INVALID" in {
      val testInput: String = "list"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(
          Some(s"list: list has exactly 1 parameter <buildings | units | technology>\nprovided: '$testInput'")))
    }
    "map list buildings input to LIST(Option(buildings))" in {
      val testInput: String = "list buildings"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(LIST(Option("buildings")))
    }
    "map list tech input to LIST(Option(technology))" in {
      val testInput: String = "list technology"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(LIST(Option("technology")))
    }
    "map list units input to LIST(Option(units))" in {
      val testInput: String = "list units"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(LIST(Option("units")))
    }
    "map help (help) input to HELP" in {
      val testInput: String = "help"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(HELP)
    }
    "map help (h) input to HELP" in {
      val testInput: String = "h"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(HELP)
    }
    "map exit (exit) input to EXIT" in {
      val testInput: String = "exit"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(EXIT)
    }
    "map quit (quit) input to EXIT" in {
      val testInput: String = "quit"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(EXIT)
    }
    "map invalid or undefined input to INVALID" in {
      val testInput: String = "test"
      controller.mapToAction(controller.inputSanitation(testInput), testInput) should be(
        INVALID(Option(f"Unknown Input: '$testInput'")))
    }
    "map (yes/y) to USER_ACCEPT" in {
      controller.mapToAction(controller.inputSanitation("y"), "y") should be(USER_ACCEPT)
      controller.mapToAction(controller.inputSanitation("yes"), "yes") should be(USER_ACCEPT)
    }
    "map (no/n) to USER_ACCEPT" in {
      controller.mapToAction(controller.inputSanitation("n"), "n") should be(USER_DECLINE)
      controller.mapToAction(controller.inputSanitation("no"), "no") should be(USER_DECLINE)
    }
    "map done to FINISH_ROUND_REQUEST" in {
      controller.mapToAction(controller.inputSanitation("done"), "done") should be(FINISH_ROUND_REQUEST)
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
      controller.toString() should be ("Unknown Input: 'test'\nEnter help to get an overview of all available commands")
    }
  }
}