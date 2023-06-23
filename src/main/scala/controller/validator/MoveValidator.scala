package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, MessageCommand, MoveCommand}
import controller.newInterpreter.KeywordType.*
import controller.newInterpreter.{InterpretedCommand, InterpretedInput, KeywordType}
import controller.validator.validatorutils.ValidatorUtils
import model.game.gamestate.IGameStateManager
import model.game.gamestate.enums.messages.MessageType.{INVALID_INPUT, MALFORMED_INPUT}
import model.utils.GameObjectUtils

case class MoveValidator(orig: String, gsm: IGameStateManager) extends IValidator:


  override def validate(input: Vector[InterpretedInput]): Either[IValidator, Option[ICommand]] =
    //val keywords = ValidatorUtils().findKeyWords(input)
    // need from and to (Coordinate/Fleet/Unit)
    val returns = input match
      case Vector(x) => MoveCommand(List(x.toString), gsm)
      case _ => InvalidCommand(orig, gsm)

    Right(Some(returns))

  /*
private def handleMove(keywordType: KeywordType): ICommand =
  keywordType match
    case TO => MoveCommand(List(), gsm)
    case _ => MessageCommand(wrongKeyword(keywordType), MALFORMED_INPUT, gsm)

private def wrongKeyword(keyword: KeywordType): String =
  val got = keyword match
    case WITH => "with"
    case FROM => "from"
    case _ => ""
  s"move command expected 'TO' but found '$got'"

private def inputFormat(str: String): String =
  s"Invalid Format found '$str' expected: move <what> to coordinate <1-A>"

private def movableDoesNotExist(str: String): String = s"Unit or Fleet with name '$str' does not exist"
*/

