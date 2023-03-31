package view.tui

import controller.Controller
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import utils.Observer
import model.game.gamestate.GameState.*
import model.game.gamestate.GameState

import java.io.{ByteArrayOutputStream, StringReader}
import java.lang.ModuleLayer

class TuiSpec extends AnyWordSpec {
  "In the TUI" should {
    val tui = Tui(Controller())
    val title: String = "TBD"
    "the game title be returned" in {
      tui.gameTitle should be(title)
    }
    "the introductory message be returned and contain game title" in {
      tui.introductionMessage should be(
        f"""Welcome to $title
           | This is an introduction
           |""".stripMargin)
    }
  }
  "The run method" should {
    val controller: Controller = Controller()
    val tui = Tui(controller)
    "return the game state EXITED eventually after a series of inputs finalized by 'exit'" in {
      val listOfInputs: Array[String] = Array("invalid", "invalid", "build", "exit")
      val currentInput = StringReader(listOfInputs.mkString("\n"))

      Console.withIn(currentInput) {
        val result: GameState = tui.run()
        result should be(EXITED)
      }
    }
    "return the game state EXITED eventually after a series of inputs finalized by 'quit'" in {
      val listOfInputs: Array[String] = Array("invalid", "invalid", "help", "build", "quit")
      val currentInput = StringReader(listOfInputs.mkString("\n"))

      Console.withIn(currentInput) {
        val result: GameState = tui.run()
        result should be(EXITED)
      }
    }
  }
}
