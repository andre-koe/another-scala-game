package view.tui

import controller.Controller

import org.scalatest.matchers.should.Matchers.*
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec {
  "In the TUI" should {
    val tui = Tui(new Controller())
    val title: String = "TBD"
    "the game title be returned" in {
      tui.gameTitle should be(title)
    }
    "the help message be returned" in {
      tui.helpMessage should be(
        f"""The following actions should be implemented:
           | > [build] <building name> | help to get an overview of all available buildings
           | > [recruit] <unit name> | help to get an overview of all available units
           | > [research] <technology name> | help to get an overview of all available technologies
           | > [exit] to quit
           | > [go back] to undo the last action
           |""".stripMargin)
    }
    "the help message contain keywords" in {
      tui.helpMessage should include("build")
      tui.helpMessage should include("recruit")
      tui.helpMessage should include("research")
      tui.helpMessage should include("exit")
      tui.helpMessage should include("go back")
    }
    "the introductory message be returned and contain game title" in {
      tui.introductionMessage should be(
        f"""Welcome to $title
           | This is an introduction
           |""".stripMargin)
    }
    "the goodbyeMessage say goodbye" in {
      tui.goodbyeMessage should be("Goodbye!")
    }
    "the response for undefined user input be returned and contain the input" in {
      val demoInput: String = "demo"
      tui.undefinedUserInputResponse(demoInput) should be(
        f"'$demoInput' is unknown enter 'h' for an overview of all commands\n"
      )
    }
    "the response for empty user input be returned and looks like" in {
      tui.undefinedUserInputResponse("") should be("Enter 'h' to get an overview of all commands\n")
    }
  }
}
