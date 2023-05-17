package controller.newInterpreter
import controller.newInterpreter.KeywordType
import controller.newInterpreter.KeywordType._
import model.game.gamestate.GameStateManager

case class KeywordTokenizedInput(token: String) extends AbstractTokenizedInput[KeywordType]:

  override def interpret(): KeywordType =
    token match
      case "to" | ">" => TO
      case "from" | "<" => FROM
      case "with" => WITH
