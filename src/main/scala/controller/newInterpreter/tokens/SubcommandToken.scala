package controller.newInterpreter.tokens

import controller.newInterpreter.tokens.AbstractToken

case class SubcommandToken(str: String) extends AbstractToken[String]:

  override def interpret(): String = str.substring(1)
