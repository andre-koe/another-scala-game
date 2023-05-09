package controller.newInterpreter
import controller.newInterpreter.KeywordType
import controller.newInterpreter.KeywordType._
import model.game.gamestate.GameStateManager

case class KeywordExpression(token: String) extends AbstractExpression[KeywordType]:

  override def interpret(): KeywordType =
    token match
      case "to" | ">" => TO
      case "from" | "<" => FROM
      case "with" => WITH
