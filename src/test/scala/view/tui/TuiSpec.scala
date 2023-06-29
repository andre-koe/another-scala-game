package view.tui

import controller.Controller
import utils.DefaultValueProvider.given_ICommandTokenizer
import utils.DefaultValueProvider.given_IController
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec
import utils.Observer
import model.game.gamestate.gamestates.*

import java.io.{ByteArrayOutputStream, StringReader}
import java.lang.ModuleLayer

class TuiSpec extends AnyWordSpec {

  "In the TUI" should {
    val tui = Tui()
    val title: String = "Srimp Shimp"
    "the game title be returned" in {
      tui.gameTitle should be(title)
    }
    "the introductory message be returned and contain game title" in {
      tui.introductionMessage should be(
        f"""Welcome to $title
           | Enter 'help' to get an overview of all available commands
           |""".stripMargin)
    }
  }
  "The run method" should {
    "return false eventually after a series of inputs finalized by 'exit'" in {
      val tui = Tui()
      val listOfInputs: Array[String] = Array("invalid", "invalid", "build", "exit")
      val currentInput = StringReader(listOfInputs.mkString("\n"))

      Console.withIn(currentInput) {
        val result: Boolean = tui.run()
        result should be(false)
      }
    }
    "return the game state EXITED eventually after a series of inputs finalized by 'quit'" in {
      val tui = Tui()
      val listOfInputs: Array[String] = Array("invalid", "invalid", "help", "build", "quit")
      val currentInput = StringReader(listOfInputs.mkString("\n"))

      Console.withIn(currentInput) {
        val result: Boolean = tui.run()
        result should be(false)
      }
    }
  }
}
