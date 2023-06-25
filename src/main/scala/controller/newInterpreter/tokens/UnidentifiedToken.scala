package controller.newInterpreter.tokens

import controller.command.ICommand
import controller.newInterpreter.tokens.AbstractToken

case class UnidentifiedToken(str: String) extends AbstractToken[String]:

  override def interpret(): String = str
