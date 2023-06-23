package controller.validator

import controller.command.ICommand
import controller.newInterpreter.{TokenizedInput, CommandType, InterpretedInput, KeywordType}
import controller.validator.{CommandValidator, IValidator, InputValidator}
import model.core.gameobjects.purchasable.IGameObject
import model.game.gamestate.IGameStateManager

import scala.annotation.tailrec

case class ValidationHandler(gsm: IGameStateManager):

  def handle(combinedExpression: TokenizedInput): Option[ICommand] =
    validate(CommandValidator(combinedExpression.orig, gsm), combinedExpression.input)

  @tailrec
  private def validate(validator: IValidator, combinedExpression: Vector[InterpretedInput]): Option[ICommand] =
    validator.validate(combinedExpression) match
      case Left(nextValidator) => validate(nextValidator, combinedExpression)
      case Right(result) => result

