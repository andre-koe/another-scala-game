package controller.command.commands

import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.messages.MessageType
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*

import scala.io
import scala.io.AnsiColor

class MessageCommandSpec extends AnyWordSpec {

  val gsm: GameStateManager = GameStateManager()

  "The MessageCommand" when {

    "invoked with an input string and MessageType.HELP" should {
      "return a GameStateManager with green colored toString containing the input" in {
        val msgCommand = MessageCommand("test", MessageType.HELP, gsm)
        msgCommand.execute().toString should be(AnsiColor.GREEN + "test" + AnsiColor.RESET)
      }
    }

    "invoked with an input string and MessageType.DEFAULT" should {
      "return a GameStateManager with non-colored toString containing the input" in {
        val msgCommand = MessageCommand("test", MessageType.DEFAULT, gsm)
        msgCommand.execute().toString should be("test")
      }
    }

    "invoked with an input string and MessageType.INVALID_INPUT" should {
      "return a GameStateManager with red colored toString containing the input + Invalid Input Wrapper" in {
        val msgCommand = MessageCommand("test", MessageType.INVALID_INPUT, gsm)
        msgCommand.execute().toString should be("Invalid Input: " + AnsiColor.RED + "'test'" + AnsiColor.RESET +
          "\nEnter 'help' to get an overview of all available commands")
      }
    }

    "invoked with an input string and MessageType.MALFORMED_INPUT" should {
      "return a GameStateManager with yellow colored toString containing the input" in {
        val msgCommand = MessageCommand("test", MessageType.MALFORMED_INPUT, gsm)
        msgCommand.execute().toString should be(AnsiColor.YELLOW + "test" + AnsiColor.RESET)
      }
    }
  }
}
