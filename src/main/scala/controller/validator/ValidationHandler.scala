package controller.validator

import controller.command.ICommand
import controller.newInterpreter.{CombinedExpression, CommandType, InterpretedExpression, KeywordType}
import controller.validator.{CommandValidator, IValidator, InputValidator}
import model.game.gamestate.GameStateManager
import model.game.purchasable.IGameObject

import scala.annotation.tailrec

case class ValidationHandler(gsm: GameStateManager):

  def handle(combinedExpression: CombinedExpression): Option[ICommand] =
    validate(CommandValidator(combinedExpression.orig, gsm), combinedExpression.input)

  @tailrec
  private def validate(validator: IValidator, combinedExpression: Vector[InterpretedExpression]): Option[ICommand] =
    validator.validate(combinedExpression) match
      case Left(nextValidator) => validate(nextValidator, combinedExpression)
      case Right(result) => result

