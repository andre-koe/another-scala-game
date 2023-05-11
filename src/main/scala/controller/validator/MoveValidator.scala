package controller.validator

import controller.command.ICommand
import controller.command.commands.{InvalidCommand, MessageCommand, MoveCommand}
import controller.newInterpreter.KeywordType.*
import controller.newInterpreter.{InterpretedCommand, InterpretedExpression, KeywordType}
import controller.validator.utils.ValidatorUtils
import model.game.gamestate.GameStateManager
import model.game.gamestate.enums.messages.MessageType.{INVALID_INPUT, MALFORMED_INPUT}
import model.utils.GameObjectUtils

case class MoveValidator(orig: String, gsm: GameStateManager) extends IValidator:

  override def validate(input: Vector[InterpretedExpression]): Either[IValidator, Option[ICommand]] =
    val keywords = ValidatorUtils().findKeyWords(input)
    // need from and to (Coordinate/Fleet/Unit)
    val returns = keywords match
      case Some(x) => handleMove(x.head)
      case _ => InvalidCommand(inputFormat(orig), gsm)

    Right(Some(returns))
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

