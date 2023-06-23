package controller.newInterpreter.tokens

import controller.newInterpreter.KeywordType.*
import controller.newInterpreter.KeywordType
import controller.newInterpreter.tokens.AbstractToken

case class KeywordToken(token: String) extends AbstractToken[KeywordType]:

  override def interpret(): KeywordType =
    token match
      case "to" | ">" => TO
      case "from" | "<" => FROM
      case "with" => WITH
