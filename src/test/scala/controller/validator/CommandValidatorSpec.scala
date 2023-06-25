package controller.validator

import controller.command.commands.{EndGameCommand, EndRoundCommand, HelpCommand, InvalidCommand, RedoCommand, UndoCommand, UserAcceptCommand, UserDeclineCommand}
import controller.newInterpreter.CommandTokenizer
import model.game.gamestate.GameStateManager
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.*
import utils.DefaultValueProvider.given_IGameValues

class CommandValidatorSpec extends AnyWordSpec {
  
  "The CommandValidator" should {
    val gsm = GameStateManager()

      "return the appropriate Command depending on the Input" when {
        "input has InterpretedCommand snack" in {
          val input = "snack"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: InvalidCommand)) =>
          }
        }

        "input has InterpretedCommand save" in {
          val input = "save"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: FileIOValidator) =>
          }
        }

        "input has InterpretedCommand build" in {
          val input = "build"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: InstantiationValidator) =>
          }
        }

        "input has InterpretedCommand recruit" in {
          val input = "recruit"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: InstantiationValidator) =>
          }
        }

        "input has InterpretedCommand research" in {
          val input = "research"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: InstantiationValidator) =>
          }
        }

        "input has InterpretedCommand sell" in {
          val input = "sell"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: SellValidator) =>
          }
        }

        "input has InterpretedCommand list" in {
          val input = "list"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: ListValidator) =>
          }
        }

        "input has InterpretedCommand move" in {
          val input = "move"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Left(_: MoveValidator) =>
          }
        }

        "input has InterpretedCommand a (Useraccept)" in {
          val input = "y"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: UserAcceptCommand)) =>
          }
        }

        "input has InterpretedCommand n (Userdecline)" in {
          val input = "n"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: UserDeclineCommand)) =>
          }
        }

        "input has InterpretedCommand (Redo)" in {
          val input = "redo"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: RedoCommand)) =>
          }
        }

        "input has InterpretedCommand (Undo)" in {
          val input = "undo"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: UndoCommand)) =>
          }
        }

        "input has InterpretedCommand done" in {
          val input = "done"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: EndRoundCommand)) =>
          }
        }

        "input has InterpretedCommand quit" in {
          val input = "quit"
          val expr = CommandTokenizer().parseInput(input)
          CommandValidator(expr.orig, gsm).validate(expr.input) should matchPattern {
            case Right(Some(_: EndGameCommand)) =>
          }
        }
      }
    }
}
