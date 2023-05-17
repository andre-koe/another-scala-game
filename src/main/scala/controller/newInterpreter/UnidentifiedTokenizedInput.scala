package controller.newInterpreter

import controller.command.ICommand
import model.game.gamestate.GameStateManager

case class UnidentifiedTokenizedInput(str: String) extends AbstractTokenizedInput[String]:

  override def interpret(): String = str
