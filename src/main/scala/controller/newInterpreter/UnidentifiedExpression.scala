package controller.newInterpreter

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class UnidentifiedExpression(str: String) extends AbstractExpression[String]:

  override def interpret(): String = str
